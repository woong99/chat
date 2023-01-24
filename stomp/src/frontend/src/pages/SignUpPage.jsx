import axios from 'axios';
import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const SignUpPage = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [nickname, setNickname] = useState('');
  const onLogin = () => {
    navigate('/login');
  };
  const onSignup = () => {
    if (
      email === '' ||
      email === undefined ||
      password === '' ||
      password === undefined ||
      nickname === '' ||
      nickname === undefined
    ) {
      alert('이메일, 비밀번호, 닉네임을 다시 확인해주세요.');
      return;
    }
    axios
      .post('http://localhost:8080/members/signup', {
        email,
        password,
        nickname,
      })
      .then((res) => {
        if (res.status === 200) {
          alert('회원가입에 성공했습니다.');
          navigate('/login');
        } else {
          alert('회원가입에 실패했습니다.');
        }
      })
      .catch((res) => alert('회원가입에 실패했습니다.'));
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

        <Form.Group className="mb-3" controlId="formBasicNickname">
          <Form.Label>닉네임</Form.Label>
          <Form.Control
            type="text"
            placeholder="Nickname"
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
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

export default SignUpPage;
