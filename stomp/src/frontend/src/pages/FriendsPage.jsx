import axios from "axios";
import React, { useEffect, useState } from "react";
import { Badge, Button, Card } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { addPrivateMessages } from "../redux/modules/stomp";

const FriendsPage = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [users, setUsers] = useState([]);
  const connectingUsers = useSelector((state) => state.stomp.connectingUsers);
  const userNickname = useSelector((state) => state.auth.user.nickname);
  const onBackPage = () => {
    navigate("/");
  };
  const onChat = (nickname) => {
    console.log("nickname", nickname);
    axios
      .post("http://localhost:8080/private-room", {
        memberOne: userNickname,
        memberTwo: nickname,
      })
      .then((res) => {
        if (res.data.messages.length > 0) {
          dispatch(addPrivateMessages(res.data.messages));
          navigate(`/private-chat?room-id=${res.data.roomId}`, {
            state: { nickname },
          });
        } else {
          navigate(`/private-chat?room-id=${res.data.roomId}`, {
            state: { nickname },
          });
        }
      });
  };
  useEffect(() => {
    axios
      .get("http://localhost:8080/members/all")
      .then((res) => setUsers(res.data));
  }, []);

  return (
    <div className="layout">
      <div className="text-center fs-4 border-bottom position-relative">
        유저 목록
        <Button className="position-absolute start-0" onClick={onBackPage}>
          뒤로가기
        </Button>
      </div>
      <div>
        {users &&
          users
            .filter((user) => user.nickname !== userNickname)
            .map((user) => (
              <Card key={user.id} className="mt-3">
                <Card.Body className="position-relative d-flex align-items-center">
                  {user.nickname}
                  {connectingUsers.findIndex(
                    (item) => item === user.nickname
                  ) === 1 && (
                    <Badge bg="primary" className="ms-1">
                      접속중
                    </Badge>
                  )}
                  <Button
                    className="position-absolute end-0 me-2"
                    onClick={() => onChat(user.nickname)}
                  >
                    채팅
                  </Button>
                </Card.Body>
              </Card>
            ))}
      </div>
    </div>
  );
};

export default FriendsPage;
