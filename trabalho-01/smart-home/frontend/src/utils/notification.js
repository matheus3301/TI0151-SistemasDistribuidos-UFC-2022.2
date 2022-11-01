import { notification } from 'antd';

export default function Notification(type, message, status) {
    if (status !== 404)
      notification[type]({
        message: message,
        duration: 3,
        placement: 'bottomRight',
      });
  }
  