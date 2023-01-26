import React, { useEffect, useRef, useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
import ChatMessage from '../components/ChatMessage';
import { addSubscription, removeSubscription } from '../redux/modules/stomp';

const ChatPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [chatList, setChatList] = useState([]);
  const subscription = useRef();
  const [chat, setChat] = useState('');
  const client = useSelector((state) => state.stomp.client);
  const userNickname = useSelector((state) => state.auth.user.nickname);
  const subscriptions = useSelector((state) => state.stomp.subscriptions);
  console.log(chatList);
  useEffect(() => {
    if (
      client !== undefined &&
      client !== '' &&
      subscriptions.findIndex((item) => item.roomId === location.search.substring(9)) === -1
    ) {
      subscription.current = client.current?.subscribe(
        `/sub/chat/room/${location.search.substring(9)}`,
        (body) => {
          const json_body = JSON.parse(body.body);
          console.log(json_body);
          setChatList((_chat_list) => [..._chat_list, json_body]);
        },
      );
      if (subscription.current) {
        dispatch(
          addSubscription({
            roomId: location.search.substring(9),
            subscription: subscription.current,
          }),
        );
      }
    }
  }, [client]);

  const publish = (chat) => {
    if (!client.current.connected) {
      return;
    }

    client.current.publish({
      destination: '/pub/chat/message',
      body: JSON.stringify({
        roomId: location.search.substring(9),
        message: chat,
      }),
    });
    setChat('');
  };
  const onBack = () => {
    navigate('/public-chat-room');
  };
  const onCloseSubscribe = () => {
    if (subscription.current === undefined || subscription.current === '') {
      const index = subscriptions.findIndex((item) => item.roomId === location.search.substring(9));
      if (index !== -1) {
        subscriptions[index].subscription.unsubscribe();
        const subscriptionInfo = {
          roomId: location.search.substring(9),
          subscription: subscriptions[index].subscription,
        };
        dispatch(removeSubscription(subscriptionInfo));
      }
    } else {
      subscription.current.unsubscribe();
      dispatch(
        removeSubscription({
          roomId: location.search.substring(9),
          subscription: subscription.current,
        }),
      );
    }
    navigate('/public-chat-room');
  };

  const onKeyPress = (e) => {
    if (e.key === 'Enter') {
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
        {location.state.roomName}
        <Button className="ms-4" onClick={onCloseSubscribe}>
          채팅방 나가기
        </Button>
      </div>
      <div>
        {chatList?.map((chat, index) => (
          <ChatMessage
            message={chat.message}
            writer={chat.writer}
            senderNickname={userNickname}
            key={index}
          />
        ))}
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

export default ChatPage;
