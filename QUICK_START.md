# 🚀 快速获取APK - GitHub Actions 在线编译指南

## 📋 完整步骤（5分钟搞定！）

### 第1步：创建GitHub账号（如果还没有）

1. 打开 https://github.com
2. 点击 **"Sign up"** 注册（免费）
3. 或直接用Google账号登录

---

### 第2步：创建新仓库

1. 登录GitHub后，点击右上角 **"+"** 号
2. 选择 **"New repository"**
3. 填写信息：
   - **Repository name**: `esp32-led-controller` （或任意名称）
   - **Description**: `ESP32-S3 LED Controller Android App`
   - **Visibility**: 选择 **Public**（公开）或 **Private**（私有）
   - **⚠️ 不要勾选** "Add a README file"
   - **⚠️ 不要勾选** "Add .gitignore"
   - **⚠️ 不要勾选** "Choose a license"
4. 点击 **"Create repository"**

---

### 第3步：上传代码

#### 方法A：使用GitHub网页上传（最简单）⭐推荐

创建仓库后，你会看到这样的页面：

```
Quick setup — if you've done this kind of thing before

…or create a new repository on the command line

…or push an existing repository from the command line
…or import code from another repository

We also have a standalone guide on cloning a repository.
```

**操作步骤：**

1. 点击 **"uploading an existing file"** 链接（页面中间位置）

2. 你会看到上传界面，执行以下操作：

   **方法：拖拽上传**
   - 打开文件管理器
   - 进入目录：`D:\tools\esp32-s3_n16r8\blink\android_app`
   - **全选所有文件和文件夹**（Ctrl+A）
   - **拖拽到GitHub的上传区域**

   **或手动点击上传：**
   - 点击 **"Choose your files"**
   - 选择 `android_app` 文件夹内的**所有内容**
   - （不要选择 android_app 文件夹本身，选择里面的文件）

3. 确保上传了这些关键文件：
   - ✅ `.github/workflows/build-apk.yml` （自动化脚本）
   - ✅ `build.gradle` （项目配置）
   - ✅ `settings.gradle`
   - ✅ `gradlew` 和 `gradlew.bat`
   - ✅ `app/` 整个文件夹
   - ✅ `gradle/` 文件夹
   - ✅ `README.md`

4. 滚动到底部：
   - **Commit message**: 保持默认 `"Initial commit"`
   - ✅ 确保选择了 **"main"** 分支
   - 点击 **"Commit changes"** 按钮
   - 在弹窗中再次点击 **"Commit changes"**

5. 等待上传完成（取决于网速，通常1-2分钟）

---

#### 方法B：使用Git命令行上传（进阶）

如果你熟悉Git命令：

```bash
# 1. 进入项目目录
cd D:\tools\esp32-s3_n16r8\blink\android_app

# 2. 初始化Git仓库
git init

# 3. 添加所有文件
git add .

# 4. 创建首次提交
git commit -m "Initial commit: ESP32 LED Controller App"

# 5. 关联远程仓库（替换YOUR_USERNAME为你的GitHub用户名）
git remote add origin https://github.com/YOUR_USERNAME/esp32-led-controller.git

# 6. 推送到GitHub
git branch -M main
git push -u origin main
```

**提示：** 如果没有安装Git，请使用方法A（网页上传）

---

### 第4步：自动编译开始！🎉

上传成功后，GitHub会**自动开始编译**：

1. 进入你的仓库主页
2. 点击顶部的 **"Actions"** 标签页
3. 你会看到 **"Build Android APK"** 工作流正在运行
4. 点击进入查看详细日志

**编译过程大约需要2-5分钟**，你会看到：

```
✅ Set up JDK 17
✅ Setup Gradle
✅ Grant execute permission for gradlew
✅ Build Debug APK with Gradle  ← 正在编译
⏳ Upload APK Artifact         ← 编译完成后上传
```

---

### 第5步：下载APK文件 📥

编译完成后（约3-5分钟）：

1. 在 **"Actions"** 页面
2. 点击左侧的 **"Build Android APK"**
3. 点击最新的运行记录（显示绿色✅的）
4. 滚动到底部的 **"Artifacts"** 区域
5. 你会看到：
   - **📦 ESP32-LED-Controller-Debug** ← 点击这个！
6. 在弹窗中点击 **"ESP32-LED-Controller-Debug"** 下载按钮
7. APK文件会下载到你的电脑（约3-5MB）

**文件名示例：** `app-debug.apk` 或 `ESP32-LED-Controller-Debug.zip`

---

### 第6步：安装到手机 📱

#### 方法A：USB数据线传输
1. 用USB线连接手机到电脑
2. 手机上允许"文件传输"模式
3. 将APK文件复制到手机
4. 在手机文件管理器中点击APK安装
5. 允许"未知来源应用安装"

#### 方法B：无线传输（更方便）
1. 手机和电脑连接同一WiFi
2. 电脑上使用微信/QQ/网盘等工具发送APK给手机
3. 手机接收后点击安装

#### 方法C：直接从GitHub下载（手机浏览器）
1. 手机浏览器访问你的GitHub仓库
2. 进入 **Actions** → **Build Android APK**
3. 点击最新运行的 **Artifacts**
4. 直接在手机上下载APK并安装

---

## 🔧 常见问题

### Q1: 上传时提示文件太大怎么办？

GitHub限制单个文件**不超过100MB**。我们的项目总大小很小（<1MB），不会有这个问题。

### Q2: 编译失败怎么办？

1. 在Actions页面查看错误日志
2. 最常见原因：
   - 缺少必要文件 → 重新上传完整项目
   - Gradle配置错误 → 检查build.gradle
3. 可以点击 **"Re-run all jobs"** 重试

### Q3: 如何重新编译更新版本？

只需：
1. 修改代码
2. 推送到GitHub（会自动触发编译）
3. 或者手动触发：Actions → Build Android APK → Run workflow

### Q4: APK安装时提示"未知来源"

Android 8.0+安全限制：
- **设置 → 安全 → 允许未知来源应用** → 开启
- 或安装时会弹出提示，点击"允许"

### Q5: Debug版和Release版的区别？

| 版本 | 用途 | 签名 | 大小 |
|------|------|------|------|
| **Debug** | 测试开发 | 调试签名 | 较大 |
| **Release** | 正式发布 | 需要配置签名 | 较小优化 |

**首次使用选择Debug版本即可！**

---

## 🎯 下一步：使用App

### 安装后的设置：

1. **打开App**
2. **输入ESP32的IP地址**（例如：`10.127.64.170`）
3. **点击 "Connect"**
4. **开始控制LED！** 🎉

### 功能测试清单：

- [ ] 电源开关（ON/OFF）
- [ ] 亮度调节滑块
- [ ] 10个颜色预设按钮
- [ ] 5种特效模式
- [ ] LED预览圆圈显示
- [ ] 状态信息实时更新

---

## 💡 高级选项（可选）

### 自动触发编译

每次你推送代码到GitHub，都会自动编译新版本APK！

**修改代码后：**
```bash
git add .
git commit -m "Update: 添加新功能"
git push
```
→ GitHub自动编译 → 下载新APK

### 手动触发编译

1. 进入仓库 → Actions
2. 选择 "Build Android APK"
3. 右侧 **"Run workflow"** 下拉菜单
4. 选择分支（通常是main）
5. 点击 **"Run workflow"** 按钮

### 配置Release签名（可选）

如果你想生成正式发布的APK：

1. 生成签名文件（keystore）
2. 在GitHub仓库设置中添加Secrets：
   - `SIGNING_KEY_BASE64`: Base64编码的keystore文件
   - `SIGNING_KEY_ALIAS`: 密钥别名
   - `SIGNING_KEY_PASSWORD`: 密钥密码
   - `STORE_PASSWORD`: keystore密码
3. 再次运行工作流，会同时生成Release版APK

**详细签名教程：** 可参考Android官方文档

---

## 📊 项目状态监控

### 查看构建历史

```
GitHub仓库 → Actions → Build Android APK
```

你会看到类似这样的列表：

| 时间 | 状态 | 触发方式 | Artifacts |
|------|------|---------|-----------|
| 2分钟前 | ✅ 成功 | Push | 📦 Debug APK |
| 1小时前 | ✅ 成功 | Manual | 📦 Debug, Release |
| 昨天 | ❌ 失败 | Push | 无 |

### 构建徽章（可在README中展示）

```markdown
![Build Status](https://github.com/YOUR_USERNAME/esp32-led-controller/actions/workflows/build-apk.yml/badge.svg)
```

显示效果：[![Build Status](https://github.com/YOUR_USERNAME/esp32-led-controller/actions/workflows/build-apk.yml/badge.svg)]

---

## 🎉 完成检查清单

完成以下步骤后，你就拥有了一个可以控制的LED App！

- [ ] ✅ 注册/登录GitHub账号
- [ ] ✅ 创建新的GitHub仓库
- [ ] ✅ 上传android_app文件夹的所有内容
- [ ] ✅ 等待GitHub Actions自动编译（2-5分钟）
- [ ] ✅ 下载生成的APK文件
- [ ] ✅ 传输APK到Android手机
- [ ] ✅ 安装APK（允许未知来源）
- [ ] ✅ 打开App，输入ESP32 IP地址
- [ ] ✅ 点击Connect连接设备
- [ ] ✅ 测试LED控制功能
- [ ] 🎉 享受智能LED控制！

---

## 🆘 需要帮助？

如果遇到问题：

1. **查看Actions日志** - 详细错误信息
2. **检查文件完整性** - 确保上传了所有文件
3. **重新运行工作流** - Actions → Run workflow
4. **查看本项目README.md** - 更详细的技术文档

---

## 📞 快速参考

**重要链接：**
- 你的仓库：`https://github.com/YOUR_USERNAME/esp32-led-controller`
- Actions页面：仓库URL + `/actions`
- 下载APK：Actions → 最新运行 → Artifacts → Download

**常用命令：**
```bash
# 查看Git状态
git status

# 查看远程仓库
git remote -v

# 推送更新
git add . && git commit -m "update" && git push
```

---

**🎊 恭喜！你现在可以通过GitHub Actions自动编译Android App了！**

整个过程：
- ✅ 无需安装Android Studio（节省20GB+空间）
- ✅ 无需配置复杂的开发环境
- ✅ 只需浏览器就能完成
- ✅ 自动化编译，省时省力
- ✅ 云端执行，不占用本地资源

**立即开始吧！5分钟后就能用到App了！** 🚀📱✨