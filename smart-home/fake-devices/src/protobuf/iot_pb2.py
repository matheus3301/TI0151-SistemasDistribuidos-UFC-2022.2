# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: protobuf/iot.proto
"""Generated protocol buffer code."""
from google.protobuf.internal import builder as _builder
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x12protobuf/iot.proto\"\xca\x01\n\x12JoinRequestMessage\x12\x0c\n\x04name\x18\x01 \x01(\t\x12+\n\x07sensors\x18\x02 \x03(\x0b\x32\x1a.JoinRequestMessage.Sensor\x12/\n\tactuators\x18\x03 \x03(\x0b\x32\x1c.JoinRequestMessage.Actuator\x1a\"\n\x06Sensor\x12\n\n\x02id\x18\x01 \x01(\x05\x12\x0c\n\x04name\x18\x02 \x01(\t\x1a$\n\x08\x41\x63tuator\x12\n\n\x02id\x18\x01 \x01(\x05\x12\x0c\n\x04name\x18\x02 \x01(\t\"!\n\x13JoinResponseMessage\x12\n\n\x02id\x18\x01 \x01(\t\"\xca\x01\n\x16SendDataRequestMessage\x12/\n\x07sensors\x18\x01 \x03(\x0b\x32\x1e.SendDataRequestMessage.Sensor\x12\x33\n\tactuators\x18\x02 \x03(\x0b\x32 .SendDataRequestMessage.Actuator\x1a#\n\x06Sensor\x12\n\n\x02id\x18\x01 \x01(\x05\x12\r\n\x05value\x18\x02 \x01(\x01\x1a%\n\x08\x41\x63tuator\x12\n\n\x02id\x18\x01 \x01(\x05\x12\r\n\x05state\x18\x02 \x01(\x08\x42\x1e\n\x17\x62r.ufc.smarthome.modelsB\x03Iotb\x06proto3')

_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, globals())
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'protobuf.iot_pb2', globals())
if _descriptor._USE_C_DESCRIPTORS == False:

  DESCRIPTOR._options = None
  DESCRIPTOR._serialized_options = b'\n\027br.ufc.smarthome.modelsB\003Iot'
  _JOINREQUESTMESSAGE._serialized_start=23
  _JOINREQUESTMESSAGE._serialized_end=225
  _JOINREQUESTMESSAGE_SENSOR._serialized_start=153
  _JOINREQUESTMESSAGE_SENSOR._serialized_end=187
  _JOINREQUESTMESSAGE_ACTUATOR._serialized_start=189
  _JOINREQUESTMESSAGE_ACTUATOR._serialized_end=225
  _JOINRESPONSEMESSAGE._serialized_start=227
  _JOINRESPONSEMESSAGE._serialized_end=260
  _SENDDATAREQUESTMESSAGE._serialized_start=263
  _SENDDATAREQUESTMESSAGE._serialized_end=465
  _SENDDATAREQUESTMESSAGE_SENSOR._serialized_start=391
  _SENDDATAREQUESTMESSAGE_SENSOR._serialized_end=426
  _SENDDATAREQUESTMESSAGE_ACTUATOR._serialized_start=428
  _SENDDATAREQUESTMESSAGE_ACTUATOR._serialized_end=465
# @@protoc_insertion_point(module_scope)
