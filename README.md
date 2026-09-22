# ESP32-S3 LED Controller - Android App

## 📱 项目简介

这是一个用于控制ESP32-S3开发板上WS2812 RGB LED的Android应用程序。

### 功能特性

- ✅ **WiFi连接控制** - 通过局域网连接ESP32设备
- ✅ **电源开关** - 一键开关LED
- ✅ **亮度调节** - 0-100%平滑调节
- ✅ **10种颜色预设** - 红、绿、蓝、暖白、青色、紫色、橙色、粉色、黄色、白色
- ✅ **5种特效模式**：
  - 💡 Static（静态）
  - 🌊 Breathing（呼吸灯）
  - ⚡ Blinking（闪烁）
  - 🌈 Rainbow（彩虹循环）
  - 🚔 Police（警灯效果）
- ✅ **实时状态显示** - LED预览圆圈，带发光效果
- ✅ **自动状态同步** - 每2秒自动刷新
- ✅ **精美UI设计** - Material Design 3风格

## 🔧 技术栈

- **语言**: Kotlin
- **最低SDK**: API 24 (Android 7.0)
- **目标SDK**: API 34 (Android 14)
- **架构**: MVVM + Retrofit
- **UI**: Material Design Components
- **网络**: OkHttp + Retrofit + Gson

## 📋 系统要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17 或更高版本
- Android SDK Build-Tools 34.0.0
- Gradle 8.2

## 🚀 快速开始

### 1. 克隆或下载项目

将 `android_app` 文件夹复制到你的开发环境。

### 2. 使用Android Studio打开项目

```bash
# 方法1：通过Android Studio打开
# File → Open → 选择 android_app 文件夹

# 方法2：命令行打开（macOS/Linux）
studio android_app

# 方法2：命令行打开（Windows）
start android_app
```

### 3. 同步Gradle依赖

首次打开项目时，Android Studio会自动同步Gradle依赖。如果没有自动同步：

- 点击 "Sync Now" 按钮
- 或菜单：File → Sync Project with Gradle Files

### 4. 连接测试设备

#### 方式A：使用真实Android手机
1. 手机开启**开发者选项**和**USB调试**
2. 用USB数据线连接电脑
3. 在手机上允许USB调试授权

#### 方式B：使用Android模拟器
1. Android Studio → Tools → Device Manager
2. 创建新的虚拟设备（推荐API 30+）
3. 启动模拟器

### 5. 运行应用

1. 点击工具栏的 ▶️ (Run) 按钮
2. 或按快捷键 `Shift+F10`
3. 选择你的设备/模拟器
4. 等待编译和安装完成

## 📖 使用说明

### 第一步：配置IP地址

1. 确保ESP32已烧录固件并连接WiFi
2. 查看ESP32串口日志获取IP地址（例如：`10.127.64.170`）
3. 在App的IP输入框中输入该地址
4. 点击 **"Connect"** 按钮

### 第二步：控制LED

#### 电源控制
- 使用滑动开关切换ON/OFF
- 或点击 "Turn ON" / "Turn OFF" 按钮

#### 调节亮度
- 拖动亮度滑块（0-100%）
- 实时预览LED亮度变化

#### 选择颜色
- 点击10个预设颜色按钮快速切换
- 当前选中的颜色会高亮显示

#### 特效模式
- 点击模式下拉框选择：
  - **Static** - 固定颜色显示
  - **Breathing** - 呼吸灯效果（柔和渐变）
  - **Blinking** - 快速闪烁（警示灯）
  - **Rainbow** - 彩虹色彩循环
  - **Police** - 红蓝警灯交替闪烁

### 第三步：查看状态

底部状态面板实时显示：
- State: ON/OFF
- Mode: 当前模式名称
- RGB: 当前RGB值
- IP: 连接的设备地址

## 🏗️ 项目结构

```
android_app/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/ledcontroller/
│   │   │   ├── MainActivity.kt          # 主界面Activity
│   │   │   ├── model/
│   │   │   │   └── LedState.kt         # 数据模型
│   │   │   ├── network/
│   │   │   │   ├── ApiService.kt       # REST API接口
│   │   │   │   └── LedApiClient.kt     # HTTP客户端
│   │   │   └── ui/
│   │   │       └── LedPreviewView.kt   # LED预览自定义视图
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml   # 主布局文件
│   │   │   ├── values/
│   │   │   │   ├── colors.xml          # 颜色定义
│   │   │   │   ├── strings.xml         # 字符串资源
│   │   │   │   └── themes.xml          # 主题样式
│   │   │   └── drawable/
│   │   │       ├── edittext_background.xml
│   │   │       └── spinner_background.xml
│   │   └── AndroidManifest.xml         # 应用清单
│   └── build.gradle                    # 应用模块构建配置
├── build.gradle                        # 项目级构建配置
├── settings.gradle                     # 项目设置
├── gradle.properties                   # Gradle属性
└── README.md                           # 项目说明
```

## 🔌 API接口说明

App通过HTTP REST API与ESP32通信：

| 接口 | 方法 | 功能 | 示例 |
|------|------|------|------|
| `/api/status` | GET | 获取LED状态 | - |
| `/api/on` | POST | 开灯 | - |
| `/api/off` | POST | 关灯 | - |
| `/api/color` | POST | 设置颜色 | `{"r":255,"g":0,"b":0}` |
| `/api/brightness` | POST | 设置亮度 | `{"brightness":50}` |
| `/api/mode` | POST | 设置模式 | `{"mode":3}` |

### 响应示例

**获取状态：**
```json
{
  "state": true,
  "r": 255,
  "g": 128,
  "b": 0,
  "brightness": 80,
  "mode": 3
}
```

## ⚙️ 自定义配置

### 修改默认IP地址

编辑 [MainActivity.kt](app/src/main/java/com/example/ledcontroller/MainActivity.kt)：

```kotlin
companion object {
    const val DEFAULT_IP = "10.127.64.170" // 修改为你的ESP32 IP
}
```

### 修改轮询间隔

在 [MainActivity.kt](app/src/main/java/com/example/ledcontroller/MainActivity.kt) 的 `startPolling()` 方法中：

```kotlin
delay(2000) // 修改为其他值（毫秒）
```

### 修改超时时间

编辑 [LedApiClient.kt](app/src/main/java/com/example/ledcontroller/network/LedApiClient.kt)：

```kotlin
private val client = OkHttpClient.Builder()
    .connectTimeout(5, TimeUnit.SECONDS)  // 连接超时
    .readTimeout(5, TimeUnit.SECONDS)      // 读取超时
    .writeTimeout(5, TimeUnit.SECONDS)     // 写入超时
    .build()
```

## 🎨 UI自定义

### 修改主题颜色

编辑 [colors.xml](app/src/main/res/values/colors.xml)：

```xml
<color name="background_start">#FF667EEA</color>  <!-- 渐变起始色 -->
<color name="background_end">#FF764BA2</color>    <!-- 渐变结束色 -->
<color name="primary">#FF2196F3</color>           <!-- 主色调 -->
```

### 修改App图标

替换以下文件：
- `app/src/main/res/mipmap-hdpi/ic_launcher.png` (72x72)
- `app/src/main/res/mipmap-mdpi/ic_launcher.png` (48x48)
- `app/src/main/res/mipmap-xhdpi/ic_launcher.png` (96x96)
- `app/src/main/res/mipmap-xxhdpi/ic_launcher.png` (144x144)
- `app/src/main/res/mipmap-xxxhdpi/ic_launcher.png` (192x192)

## 🐛 常见问题

### Q1: 无法连接到ESP32

**可能原因及解决方案：**

1. **IP地址错误**
   - 检查ESP32串口输出的实际IP地址
   - 确保手机和ESP32在同一局域网

2. **防火墙阻止**
   - 暂时关闭手机或电脑的防火墙
   - 确保ESP32的80端口未被占用

3. **ESP32未启动Web Server**
   - 检查ESP32是否成功连接WiFi
   - 查看串口日志确认 "Web server started"

### Q2: App编译错误

**解决方案：**

1. 清理并重建项目：
   ```
   Build → Clean Project
   Build → Rebuild Project
   ```

2. 检查JDK版本：
   ```
   File → Settings → Build → Gradle → Gradle JDK
   确保选择 JDK 17
   ```

3. 更新SDK：
   ```
   Tools → SDK Manager → 安装 SDK 34
   ```

### Q3: LED不响应控制

**排查步骤：**

1. 检查ESP32串口是否有错误日志
2. 用浏览器访问 `http://ESP32_IP/api/status` 测试API
3. 确认ESP32固件是最新版本（包含Web Server功能）

### Q4: App运行缓慢

**优化建议：**

1. 减小轮询间隔（修改为3000-5000ms）
2. 检查网络延迟
3. 关闭不必要的后台应用

## 📱 支持的设备

- **最低要求**: Android 7.0 (API 24)
- **推荐**: Android 10+ (API 29+)
- **测试设备**: 
  - Pixel 6 (Android 13)
  - Samsung Galaxy S21 (Android 12)
  - Xiaomi Mi 11 (Android 11)

## 🔒 权限说明

App需要以下权限：

- **INTERNET** - 与ESP32通信
- **ACCESS_NETWORK_STATE** - 检测网络状态

权限已在 [AndroidManifest.xml](app/src/main/AndroidManifest.xml) 中声明。

## 📄 许可证

MIT License

## 👨‍💻 开发者

ESP32-S3 LED Controller Team

## 🤝 贡献

欢迎提交Issue和Pull Request！

---

**享受控制LED的乐趣吧！** 🎉✨