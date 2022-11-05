import Devices from './components/Devices/devices'
import './styles/global.css';
import {Layout, Button, PageHeader} from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import {useState} from 'react';
const { Content } = Layout;

function App() {
  const [devicesList, setDevicesList] = useState([]);
  return (
    <div className="app-container">
      <Layout>
          <PageHeader
            className="site-page-header"
            title="SMARTHOME"
          />
        <Content style={{ padding: '0 50px'}}>
          <Devices devicesList={devicesList} setDevicesList={setDevicesList} />
        </Content>
      </Layout>
    </div>
  );
}

export default App;
