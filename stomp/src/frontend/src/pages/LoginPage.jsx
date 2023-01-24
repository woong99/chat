import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { login } from '../redux/modules/auth';

const LoginPage = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const onSignup = () => {
    navigate('/signup');
  };
  const onLogin = () => {
    dispatch(login({ email, password })).then(navigate('/'));
  };
  return (
    <div className="layout">
      <div className="text-center fs-1 mt-5">STOMP</div>
      <Form className="mt-5">
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>이메일</Form.Label>
          <Form.Control
            type="email"
            placeholder="Enter email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>비밀번호</Form.Label>
          <Form.Control
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </Form.Group>

        <Button variant="primary" className="w-100 mt-5 fs-5" onClick={onSignup}>
          회원가입
        </Button>
        <Button variant="primary" className="w-100 mt-3 fs-5" onClick={onLogin}>
          로그인
        </Button>
      </Form>
    </div>
  );
};

export default LoginPage;
