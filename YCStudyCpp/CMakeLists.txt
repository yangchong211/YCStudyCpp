cmake_minimum_required(VERSION 3.24)
project(YCStudyCC)

set(CMAKE_CXX_STANDARD 17)

# c++
# 01.第一天：基础语法
# 02.第二天：运算符和表达式
# 03.第三天：判断和循环
# 04.第四天：数组和容器
# 05.第五天：指针和引用
# 06.第六天：函数实践
# 07.第七天：IO输入和输出
# 08.第八天：类和对象
# 09.第九天：继承和派生
# 10.第十天：多线程和并发
# 11.第十一天：线程安全锁
# 12.第十二天：内存分配堆和栈
# 13.第十三天：异常处理
# 14.第十四天：STL标准模板库
# 15.第十五天：设计模式
# 16.第十六天：程序调试
# 17.第十七天：网络通信
# 18.第十八天：综合案例

# https://juejin.cn/post/6947657958419333151


# day1 基础教程
# day2 注释
# day3 数据类型：基本的内置类型，typedef 声明，枚举类型，类型转换
# day4 变量类型
# day5 作用域
# day6 常量
# day7 修饰副，存储类
# day8 运算符
# day9 语句。循环，判断
# day10 函数
# day11 数字
# day12 数组
# day13 字符串
# day14 指针
# day15 引用
# day16 时间
# day18 数据结构
# day19 类和对象
# day20 继承
# day21 重载
# day22 多态

# day24 文件和流
# day25 异常处理
# day27 命名空间

add_executable(YCStudyCC
        main.cpp
        # 下面是c++
        day1/day1_5_string.cpp
        day1/day1_3_data.cpp
        day1/day1_1_hello.cpp
        day1/day1_2_designator.cpp
        study1/day3_data_type.cpp
        study1/day4_array.cpp
        day5/day5_2_reference.cpp
        study1/day6_val.cpp
        # 07.第七天：IO输入和输出
        day7/day7_1_io.cpp
        day8/day8_struct_class.cpp
        study1/day9_yuju.cpp
        day9/day9_extend.cpp
        day9/day9_overive.cpp
        day10/day10_thread.cpp
        study1/day11_num.cpp
        study1/day12_array.cpp
        day13/day13_exception.cpp
        study1/day16_time.cpp
        study1/day19_class.cpp
        study1/day20_extend.cpp
        day9/day9_duotai.cpp
        day9/day9_abs.cpp
        day7/day7_file_io.cpp
        day12/day12_memory.cpp
        study1/day27_space.cpp
        day6/day6_template.cpp
        day15/day15_define.cpp
        day15/day15_signal.cpp
        day7/day7_2_file.cpp
        study1/day33_.cpp
        study2/text.cpp
        study4/5.cpp
        study4/51.cpp
        study4/graduateManager.h
        study4/StudentManager.cpp
        study4/StudentManager.h
        test/StudentManager.cpp
        day10/day10_pthread.cpp
        day8/day8_class_template.cpp
        day8/day8_namespace.cpp
        day12/day12_std_ptr.cpp
        day14/day14_1_stl.cpp
        day14/day14_2_algorithms.cpp day14/day14_5_fun.cpp day1/day1_4_val.cpp day14/day14_3_iterator.cpp day14/day14_4_adapter.cpp day11/day11_1_synchronization.cpp day4/day4_1_fun.cpp day4/day4_2_param.cpp day4/day4_3_invoke.cpp day4/day4_3_template.cpp day3/day3_1_if.cpp day3/day3_2_while.cpp day5/day5_1_reference.cpp)
