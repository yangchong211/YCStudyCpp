//
// Created by 杨充 on 2023/6/8.
//

#include <iostream>
using namespace std;

//循环
void test1();
//判断
void test2();

int main() {
    test1();
    test2();
    return 0;
}

//循环类型
//while 循环	当给定条件为真时，重复语句或语句组。它会在执行循环主体之前测试条件。
//for 循环	多次执行一个语句序列，简化管理循环变量的代码。
//do...while 循环	除了它是在循环主体结尾测试条件外，其他与 while 语句类似。
//嵌套循环	您可以在 while、for 或 do..while 循环内使用一个或多个循环。

//循环控制语句
//break 语句	终止 loop 或 switch 语句，程序流将继续执行紧接着 loop 或 switch 的下一条语句。
//continue 语句	引起循环跳过主体的剩余部分，立即重新开始测试条件。
//goto 语句	将控制转移到被标记的语句。但是不建议在程序中使用 goto 语句。
void test1(){
    int i = 0;
    for (;;) {
        i++;
        if (i > 3 && i< 6){
            continue;
        }
        if (i == 10){
            break;
        }
        printf("This loop will run forever.%d \n" ,i);
    }
}

//判断
//if 语句	一个 if 语句 由一个布尔表达式后跟一个或多个语句组成。
//if...else 语句	一个 if 语句 后可跟一个可选的 else 语句，else 语句在布尔表达式为假时执行。
//嵌套 if 语句	您可以在一个 if 或 else if 语句内使用另一个 if 或 else if 语句。
//switch 语句	一个 switch 语句允许测试一个变量等于多个值时的情况。
//嵌套 switch 语句	您可以在一个 switch 语句内使用另一个 switch 语句。
void test2(){
    //三目运算嵌套
    int a,b,c,d,max;
    cout<<"请输入三个数字：";
    cin>>a>>b>>c;
    max=(d=a>=b?a:b)>=c?d:c;
    cout<<"最大值为："<<max<<endl;

    //求 a,b,c,d 四个数中的最大数。
    int m, n,z;
    a = 10;
    b = 20;
    c = 30;
    d = 40;
    m = a > b ? a : b;
    n = c > d ? c : d;
    z = m > n ? m : n;
    cout<<"最大值为："<<z<<endl;
}
