//
// Created by 杨充 on 2024/6/12.
//
#include <iostream>
using namespace std;

//8.4.1.1 命名空间场景介绍
void test8_4_1_1();
//8.4.1.2 定义命名空间
void test8_4_1_2();
//8.4.1.3 using 指令
void test8_4_1_3();
//8.4.1.4 不连续的命名空间
void test8_4_1_4();
//8.4.1.5 嵌套的命名空间
void test8_4_1_5();

int main() {
    test8_4_1_1();
    test8_4_1_2();
    test8_4_1_3();
    test8_4_1_4();
    test8_4_1_5();
    return 0;
}


//8.4.1.1 命名空间场景介绍
void test8_4_1_1() {
    //假设这样一种情况，当一个班上有两个名叫 Zara 的学生时，为了明确区分它们，我们在使用名字之外，不得不使用一些额外的信息，比如他们的家庭住址，或者他们父母的名字等等。
    //同样的情况也出现在 C++ 应用程序中。例如，您可能会写一个名为 xyz() 的函数，在另一个可用的库中也存在一个相同的函数 xyz()。这样，编译器就无法判断您所使用的是哪一个 xyz() 函数。
    //因此，引入了命名空间这个概念，专门用于解决上面的问题，它可作为附加信息来区分不同库中相同名称的函数、类、变量等。
    //使用了命名空间即定义了上下文。本质上，命名空间就是定义了一个范围。
}


//8.4.1.2 定义命名空间
namespace first_space {
    void fun() {
        cout << "Inside first_space" << endl;
    }
}
namespace second_space {
    void fun() {
        cout << "Inside second_space" << endl;
    }
}
void test8_4_1_2() {
    cout << "8.4.1.2 定义命名空间" << endl;
    // 调用第一个命名空间中的函数
    first_space::fun();
    // 调用第二个命名空间中的函数
    second_space::fun();
}

//8.4.1.3 using 指令
using namespace first_space;
//using 指令引入的名称遵循正常的范围规则。名称从使用 using 指令开始是可见的，直到该范围结束。此时，在范围以外定义的同名实体是隐藏的。
void test8_4_1_3() {
    cout << "8.4.1.3 using 指令" << endl;
    fun();
}

//8.4.1.4 不连续的命名空间
//命名空间可以定义在几个不同的部分中，因此命名空间是由几个单独定义的部分组成的。一个命名空间的各个组成部分可以分散在多个文件中。
namespace first_space {
    void fun1() {
        cout << "Inside first_space，你是个逗比" << endl;
    }
}
void test8_4_1_4() {
    cout << "8.4.1.4 不连续的命名空间" << endl;
    fun1();
}

//8.4.1.5 嵌套的命名空间
namespace first_sp{
    void funSp() {
        cout << "Inside first_sp，逗比充1" << endl;
    }
    namespace second_sp {
        void funSp() {
            cout << "Inside second_sp，逗比充2" << endl;
        }
    }
}
using namespace first_sp::second_sp;
void test8_4_1_5() {
    cout << "8.4.1.5 嵌套的命名空间" << endl;
    // 调用第二个命名空间中的函数
    funSp();
}
