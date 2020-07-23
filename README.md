# 开眼

## 项目展示

### 启动，背景来自必应每日一图，每天不同

 ![image](https://github.com/LinT0T/OpenEye/blob/master/gif/启动.gif)
 
 ![image](https://github.com/LinT0T/OpenEye/blob/master/gif/启动2.png)

### 切换动画

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/切换动画.gif)

### 下拉刷新，上拉加载

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/上拉刷新下拉加载.gif)

### 发现

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/发现.gif)

### 日报

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/日报.gif)

### 推荐

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/推荐.gif)

### 关注

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/关注.gif)

### 通知

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/通知关注.gif)

### 播放

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/播放.gif)

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/可播放.gif)

### 分类

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/分类.gif)

### 搜索

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/搜索.gif)

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/再次搜索.gif)

### 传统艺能，通知栏

![image](https://github.com/LinT0T/OpenEye/blob/master/gif/广播.gif)

## 主要技术及第三方库

### MVVM + AndroidX + JetPack

主要攻克难点

### 自定义View

自定义ViewPager解决滑动冲突

### BottomNavigationView + ViewPager + Fragment + Tablayout

首页实现方式

### 广播

传统艺能，不多说了

### 动画

启动页实现

### Retrofit2

网络请求

### Glide

常用库

### Immersionbar

实现沉浸式状态栏

### Aboomy:banner

发现页Banner

### View-pager-transforms

ViewPager切换动画库

### ConsecutiveScroller

实现整页滑动

### GSYVideoPlayer

视频播放页播放器，基于bilibili开源播放器IjkVideoPlayer

### Htextview

播放页实现与开眼一样标题，作者，简介打字机效果

### SmartRefreshLayout

实现上拉刷新，下拉加载

### Broccoli

实现占位图

### Searchbox

实现仿bilibili搜索

## 声明 

项目中所有API接口部分来自网络，部分自己抓取自开眼app，所有版权均归属开眼app所有，仅供学习借鉴使用，请勿用做商业用途。



## 一些感想

登陆注册和banne的api抓取不到，功能没有实现，有点遗憾。感觉这次的项目模仿UI方面做的挺好，使用了MVVM架构，使用了kotlin语言，当我在写这个项目的时候，从一开始使用kotlin卡卡顿顿，怎么使用可变List都要想很久，到能熟练的敲下一行行kotlin代码，从MVVM根本不会用到轻车熟路完成一次网络请求，UI更新，这次项目真的给我很大的提升，是我之前完全不能想象的。最后要感谢所有的学长学姐同学们，谢谢你们给予的帮助！

## 2020/7/24 更新

1.解决了一个由开眼后端引起首页推荐不能加载的bug

 原因：开眼后端返回一个无title的json数据，这是我从开始做这个项目以来从未见过的，所以也没进行处理
 
 解决方法：加一个try catch不加载错误的数据
 
2.添加了查看全部分类功能

 分类有一些不能加载或与标题不对应是开眼后端的问题，开眼返回的分类id与分类详情的id不一致，目前无法解决
 
3.添加了首页右上角漫游历的功能

 一个坑：ConsecutiveScroller + SmartRefreshLayout会导致recyclerview显示不全
 
4.一些细节优化，添加了更好的占位图等

