import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

export const login = createAsyncThunk(
  'POST/MEMBERS/LOGIN',
  async ({ email, password }, thunkApi) => {
    const res = await axios.post('http://localhost:8080/members/login', { email, password });

    if (res.status === 200) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${res.data.accessToken}`;
      thunkApi.dispatch(getUser());
    }
    return res.data;
  },
);

export const getUser = createAsyncThunk('GET/MEMBERS/ME', async () => {
  const res = await axios.get('http://localhost:8080/members/me');
  return res.data;
});

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    token: '',
  },
  extraReducers: (builder) => {
    builder
      .addCase(login.fulfilled, (state, action) => {
        state.token = action.payload.accessToken;
        state.expirationTime = action.payload.tokenExpiresIn;
      })
      .addCase(getUser.fulfilled, (state, action) => {
        state.user = action.payload;
      });
  },
});

export default authSlice;
