//
// Created by 杨充 on 2023/7/3.
//

#include <iostream>
#include <string>

using namespace std;

//C++ 模板
//模板是泛型编程的基础，泛型编程即以一种独立于任何特定类型的方式编写代码。
//模板是创建泛型类或函数的蓝图或公式。库容器，比如迭代器和算法，都是泛型编程的例子，它们都使用了模板的概念。

template <typename T>
inline T const& Max(T const& a , T const& b){
    return a < b ? b : a;
}

int MaxInt(int a , int b){
    return a < b ? b : a;
}

int main() {
    int i= 39;
    int j= 20;
    cout << "Max(i , j) " << Max(i , j) << endl;
    cout << "MaxInt(i , j) " << MaxInt(i , j) << endl;

    double f1 = 13.5;
    double f2 = 20.7;
    cout << "Max(f1, f2): " << Max(f1, f2) << endl;
}