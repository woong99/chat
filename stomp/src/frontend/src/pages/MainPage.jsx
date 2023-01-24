import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Button, Card, Form } from 'react-bootstrap';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

const MainPage = () => {
  const navigate = useNavigate();
  const s = useSelector((s) => s.auth.user);
  const [roomName, setRoomName] = useState('');
  const [publicChatRooms, setPublicChatRooms] = useState();
  const onChatPage = (roomId) => {
    navigate(`/chat?room-id=${roomId}`);
  };
  const onCreateRoom = () => {
    axios
      .post('http://localhost:8080/public-room', { name: roomName })
      .then((res) => {
        if (res.status === 200) {
          alert('방 만들기에 성공했습니다.');
          navigate(0);
        } else {
          alert('방 만들기에 실패했습니다.');
        }
      })
      .catch(() => {
        alert('방 만들기에 실패했습니다.');
      });
    setRoomName('');
  };

  useEffect(() => {
    axios.get('http://localhost:8080/public-room').then((res) => setPublicChatRooms(res.data));
  }, []);

  return (
    <div className="layout position-relative">
      <div className="text-center fs-4 border-bottom">{s && s.nickname}</div>
      <div>
        {publicChatRooms &&
          publicChatRooms.map((room) => (
            <Card key={room.roomId} className="mt-3" onClick={() => onChatPage()}>
              <Card.Body>{room.name}</Card.Body>
            </Card>
          ))}
      </div>
      <div className="position-absolute bottom-0 start-0 end-0">
        <Form.Control
          type="text"
          placeholder="방 이름"
          value={roomName}
          onChange={(e) => setRoomName(e.target.value)}
        />
        <Button variant="primary" className="w-100" onClick={onCreateRoom}>
          방 만들기
        </Button>
      </div>
    </div>
  );
};

export default MainPage;
