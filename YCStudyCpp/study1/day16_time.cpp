//
// Created by 杨充 on 2023/6/8.
//

#include <iostream>
using namespace std;
//当前日期和时间
void test1();
//使用结构 tm 格式化时间
void test2();
void test3();


//C++ 日期 & 时间
//有四个与时间相关的类型：clock_t、time_t、size_t 和 tm。类型 clock_t、size_t 和 time_t 能够把系统时间和日期表示为某种整数。

int main(){
    test1();
    test2();
    return 0;
}

//当前日期和时间
//下面的实例获取当前系统的日期和时间，包括本地时间和协调世界时（UTC）。
void test1(){
    time_t now = ::time(0);
    //定义一个char类型的指针
    //&now，是引用
    char* dt = ::ctime(&now);
    cout << "本地日期和时间：" << dt << endl;

    tm *gmtm = ::gmtime(&now);
    dt = ::asctime(gmtm);
    cout << "UTC 日期和时间：" << dt << endl;
}

//使用结构 tm 格式化时间
//tm 结构在 C/C++ 中处理日期和时间相关的操作时，显得尤为重要。
//tm 结构以 C 结构的形式保存日期和时间。大多数与时间相关的函数都使用了 tm 结构。
void test2(){
    // 基于当前系统的当前日期/时间
    time_t now = time(0);

    cout << "1970 到目前经过秒数:" << now << endl;

    tm *ltm = localtime(&now);

    // 输出 tm 结构的各个组成部分
    cout << "年: "<< 1900 + ltm->tm_year << endl;
    cout << "月: "<< 1 + ltm->tm_mon<< endl;
    cout << "日: "<<  ltm->tm_mday << endl;
    cout << "时间: "<< ltm->tm_hour << ":";
    cout << ltm->tm_min << ":";
    cout << ltm->tm_sec << endl;
}

void test3(){

}