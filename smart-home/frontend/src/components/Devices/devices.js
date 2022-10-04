import { useEffect } from 'react';
import PropTypes from 'prop-types';
import {getDevices, toggleActuator, deleteDevice} from '../../services/protobuf'
import { Row, Col, Card, Button} from 'antd';
import {DeleteOutlined} from '@ant-design/icons'
import { Area } from '@ant-design/plots';
import './devices.css';

const Devices = ({devicesList, setDevicesList}) => {

    const config = {
      xField: 'timestamp',
      yField: 'value',
      xAxis: {
        range: [0, 1],
      },
      animation: false,
      areaStyle: () => {
        return {
          fill: 'l(270) 0:#ffffff 0.5:#7ec2f3 1:#1890ff',
        };
      },
      meta: {
          timestamp: {formatter: (e) => {
              var options = {
                  hour: 'numeric',
                  minute: 'numeric',
              }
              return new Date(e).toLocaleDateString('pt-BR', options);
          }}
      }
    };

    useEffect(() => {
        getDevices(setDevicesList);
    }, [setDevicesList])

    useEffect(() => {
        const interval = setInterval(() => {
          getDevices(setDevicesList);
        }, 5000);
        return () => clearInterval(interval);
      }, [setDevicesList]);

    return (
        <>
        <Row type='flex' gutter={[48, 48]}>
            {devicesList?.map( (device) =>
                (
                    <Col xs={24} xl={12} key={device.uuid}>
                        <Card
                            title={device.name} 
                            key={device.uuid} 
                            extra={<Button danger icon={<DeleteOutlined />} size='large' onClick={() => {deleteDevice(device.uuid, setDevicesList)}}/>}
                            >
                            <div className='button-list'>
                                {device.actuators?.map( (actuator) => (
                                    <Button key={actuator.id} danger={actuator.history?.slice(-1)[0].value === true ? false : true } onClick={ () => {toggleActuator(device.uuid, actuator.id, setDevicesList)}} type="primary">{actuator.name}</Button>
                                ))}
                            </ div>
                            {device.sensors?.map( (sensor) =>
                                (
                                    <Card key={sensor.id} title={sensor.name}>
                                        {sensor.history && (
                                            <Area data={sensor?.history} {...config}/>
                                        )}
                                    </ Card>
                                )
                            )}
                        </Card>
                    </Col>
                )
            )}             
        </Row>
        </>
    );
};

Devices.propTypes = {
    devicesList: PropTypes.array.isRequired,
    setDevicesList: PropTypes.func.isRequired,
}
Devices.defaultProps = {
    devicesList: [],
}
export default Devices;