import axios from 'axios';
import React, { useEffect, useRef, useState } from 'react';
import { Button } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

const MainPage = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  return (
    <div className="layout">
      <div className="d-flex justify-content-between">
        <Button
          onClick={() => {
            navigate('/public-chat-room');
          }}
        >
          공개 채팅방
        </Button>
        <Button
          onClick={() => {
            navigate('/friends');
          }}
        >
          친구 목록
        </Button>
        <Button
          onClick={() => {
            navigate('/private-chat-room');
          }}
        >
          개인 채팅방
        </Button>
      </div>
    </div>
  );
};

export default MainPage;
