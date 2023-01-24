import { configureStore } from '@reduxjs/toolkit';
import authSlice from './modules/auth';

const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
  },
});

export default store;
