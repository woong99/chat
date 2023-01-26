import React, { useEffect, useRef, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import ChatMessage from "../components/ChatMessage";

const ChatPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [chatList, setChatList] = useState([]);
  const subscription = useRef();
  const [chat, setChat] = useState("");
  const client = useSelector((state) => state.stomp.client);
  const userNickname = useSelector((state) => state.auth.user.nickname);

  useEffect(() => {
    console.log(client);
    if (client !== undefined && client !== "") {
      subscription.current = client.current?.subscribe(
        `/sub/chat/room/${location.search}`,
        (body) => {
          const json_body = JSON.parse(body.body);
          console.log(json_body);
          setChatList((_chat_list) => [..._chat_list, json_body]);
        }
      );
    }
  }, [client]);

  const publish = (chat) => {
    if (!client.current.connected) {
      return;
    }

    client.current.publish({
      destination: "/pub/chat/message",
      body: JSON.stringify({
        roomId: location.search,
        message: chat,
      }),
    });
    setChat("");
  };
  const onBack = () => {
    navigate("/public-chat-room");
  };
  const onCloseSubscribe = () => {
    console.log(subscription.current);
    subscription.current.unsubscribe();
    console.log(subscription.current);
    navigate("/public-chat-room");
  };
  return (
    <div className="layout position-relative">
      <div className="text-center fs-4 border-bottom d-flex justify-content-center">
        <Button className="me-4" onClick={onBack}>
          뒤로가기
        </Button>
        {location.state.roomName}
        <Button className="ms-4" onClick={onCloseSubscribe}>
          채팅방 나가기
        </Button>
      </div>
      <div>
        {chatList?.map((chat) => (
          <ChatMessage
            message={chat.message}
            writer={chat.writer}
            senderNickname={userNickname}
            key={chat.message}
          />
        ))}
      </div>
      <div className="position-absolute bottom-0 start-0 end-0">
        <Form.Control
          type="text"
          placeholder="내용을 입력해주세요."
          value={chat}
          onChange={(e) => setChat(e.target.value)}
        />
        <Button
          variant="primary"
          className="w-100"
          onClick={() => publish(chat)}
        >
          전송
        </Button>
      </div>
    </div>
  );
};

export default ChatPage;
