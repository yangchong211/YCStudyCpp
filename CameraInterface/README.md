## 一. 简介

Camera 提供Android终端通用的Camera接口规范，实现方便拓展不同平台框架的接入，对上层业务提供通用、统一、轻量级的标准协议。

## 二. 应用场景
不同厂商定制化录制服务差异较大，通过统一的接口适配能够快速实现不同厂商之间录制服务切换，从而达到上层业务与三方录制服务之间完全解耦的目的。

## 三. 接入
1.build.gradle 文件中添加接口依赖：
2.实现相关接口。
3. 编译打包成aar，提供给业务App依赖

## 四.示例Demo

## 五. 总体设计
第一层`业务App逻辑层` 只通过接口进行通信
第二层`ICamera SDK`是 ICamera 通用的 API 接口规范。
第三层`Adapter`层，适配层根据第三方录制平台做适配。
第四层`三方Camera SDK`,由第三方提供的录制服务层。
App 统一调用`DCamera SDK`中的 API，`DCamera SDK`调用具体的`Camera Adapter`，由`Camera Adapter`调用对应的三方`Camera SDK`。

