import Devices from './components/Devices/devices'
import './styles/global.css';
import {Layout, Button, PageHeader} from 'antd';
import { SearchOutlined } from '@ant-design/icons';
const { Content } = Layout;

function App() {
  return (
    <div className="app-container">
      <Layout>
          <PageHeader
            className="site-page-header"
            title="SMARTHOME"
            extra={[
              <Button icon={<SearchOutlined />}>Buscar Dispositivos</Button>,
            ]}
          />
        <Content>
          <Devices />
        </Content>
      </Layout>
    </div>
  );
}

export default App;
