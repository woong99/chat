import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import ChatMessage from "../components/ChatMessage";
import { addPrivateMessages } from "../redux/modules/stomp";

const PrivateChatPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const subscription = useRef();
  const scrollRef = useRef();
  const [chat, setChat] = useState("");
  // const [chatList, setChatList] = useState([]);
  const client = useSelector((state) => state.stomp.client);
  const userNickname = useSelector((state) => state.auth.user.nickname);
  const token = useSelector((state) => state.auth.token);
  const chatList = useSelector((state) => state.stomp.privateMessages);
  const enterUsers = useSelector((state) => state.stomp.enterUsers);
  const headers = { Authorization: `Bearer ${token}` };
  const roomId = location.search.substring(9);
  console.log("chatList", chatList);
  console.log("enterUsers", enterUsers);
  useEffect(() => {
    let clientRefValue = null;
    if (client.current) {
      client.current.publish({
        destination: "/pub/private",
        headers,
        body: JSON.stringify({
          roomId,
          writer: "",
          receiver: location.state.nickname,
          message: "",
          command: "ENTER",
          isRead: "",
        }),
      });
      clientRefValue = client.current;
    }
    // axios
    //   .post("http://localhost:8080/private-room/message-list", { roomId })
    //   .then((res) => dispatch(addPrivateMessages(res.data)));
    return () => {
      clientRefValue.publish({
        destination: "/pub/private",
        headers,
        body: JSON.stringify({
          roomId: location.search.substring(9),
          writer: "",
          receiver: location.state.nickname,
          message: "",
          command: "OUT",
          isRead: "",
        }),
      });
    };
  }, []);

  useEffect(() => {
    scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
  }, [chatList]);
  const publish = (chat) => {
    if (!client.current.connected) {
      return;
    }

    client.current.publish({
      destination: "/pub/private",
      headers,
      body: JSON.stringify({
        roomId,
        writer: "",
        receiver: location.state.nickname,
        message: chat,
        isRead:
          enterUsers.findIndex(
            (e) => e.roomId === roomId && e.user === location.state.nickname
          ) !== -1
            ? "Y"
            : "N",
        command: "MESSAGE",
      }),
    });
    setChat("");
  };

  const onBack = () => {
    navigate("/friends");
  };
  const onKeyPress = (e) => {
    if (e.key === "Enter") {
      onPublish();
    }
  };

  const onPublish = () => {
    publish(chat);
  };
  return (
    <div className="layout position-relative">
      <div className="text-center fs-4 border-bottom d-flex justify-content-center">
        <Button className="me-4" onClick={onBack}>
          뒤로가기
        </Button>
        {location.state.nickname}
        <Button className="ms-4">채팅방 나가기</Button>
      </div>
      <div className="messages-container" ref={scrollRef}>
        {chatList?.map(
          (chat, index) =>
            chat.roomId === roomId && (
              <ChatMessage
                message={chat.message}
                writer={chat.writer}
                senderNickname={userNickname}
                isRead={chat.isRead}
                key={index}
              />
            )
        )}
      </div>
      <div className="position-absolute bottom-0 start-0 end-0">
        <Form.Control
          type="text"
          placeholder="내용을 입력해주세요."
          value={chat}
          onChange={(e) => setChat(e.target.value)}
          onKeyDown={onKeyPress}
        />
        <Button variant="primary" className="w-100" onClick={onPublish}>
          전송
        </Button>
      </div>
    </div>
  );
};

export default PrivateChatPage;
