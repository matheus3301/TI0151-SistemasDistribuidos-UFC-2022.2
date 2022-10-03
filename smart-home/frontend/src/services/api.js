import axios from axios;

const api = axios.create({
    baseurl: process.env.REACT_APP_API_URL,
    headers: {
        'Content-Type': 'application/x-protobuf;charset=UTF-8'
    }
})

export default api;