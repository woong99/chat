import axios from "axios";
import React, { useEffect, useState } from "react";
import { Form, Button, Card, Badge } from "react-bootstrap";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const PublicChatRoomsPage = () => {
  const navigate = useNavigate();
  const [roomName, setRoomName] = useState("");
  const [publicChatRooms, setPublicChatRooms] = useState();
  const subscriptions = useSelector((state) => state.stomp.subscriptions);
  const user = useSelector((state) => state.auth.user);
  const onChatPage = (roomId, roomName) => {
    navigate(`/chat?room-id=${roomId}`, { state: { roomName } });
  };
  const onBackPage = () => {
    navigate("/");
  };
  const onCreateRoom = () => {
    if (roomName === "" || roomName === undefined) {
      alert("방 이름을 설정해주세요.");
      return;
    }
    axios
      .post("http://localhost:8080/public-room", {
        name: roomName,
      })
      .then((res) => {
        if (res.status === 200) {
          alert("방 만들기에 성공했습니다.");
          navigate(0);
        } else {
          alert("방 만들기에 실패했습니다.");
        }
      })
      .catch(() => {
        alert("방 만들기에 실패했습니다.");
      });
    setRoomName("");
  };

  useEffect(() => {
    axios
      .get("http://localhost:8080/public-room")
      .then((res) => setPublicChatRooms(res.data));
  }, []);

  return (
    <div className="layout position-relative">
      <div className="text-center fs-4 border-bottom position-relative">
        {user && user.nickname}
        <Button className="position-absolute start-0" onClick={onBackPage}>
          뒤로가기
        </Button>
      </div>
      <div>
        {publicChatRooms &&
          publicChatRooms.map((room) => (
            <Card
              key={room.roomId}
              className="mt-3"
              onClick={() => onChatPage(room.roomId, room.name)}
            >
              <Card.Body className="position-relative">
                {room.name}
                {subscriptions.findIndex(
                  (item) => item.roomId === room.roomId
                ) !== -1 && (
                  <Badge bg="primary" className="position-absolute end-0 me-4">
                    {subscriptions.find((item) => item.roomId === room.roomId)
                      .notRead === 0
                      ? "구독중"
                      : subscriptions.find(
                          (item) => item.roomId === room.roomId
                        ).notRead}
                  </Badge>
                )}
              </Card.Body>
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

export default PublicChatRoomsPage;
