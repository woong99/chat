import React from 'react';
import { Button, Form } from 'react-bootstrap';
import { useLocation } from 'react-router-dom';

const ChatPage = () => {
  const location = useLocation();

  return (
    <div className="layout position-relative">
      <div className="text-center fs-4 border-bottom">{location.search.substring(9)}</div>
      <div></div>
      <div className="position-absolute bottom-0 start-0 end-0">
        <Form.Control type="text" placeholder="방 이름" />
        <Button variant="primary" className="w-100">
          전송
        </Button>
      </div>
    </div>
  );
};

export default ChatPage;
