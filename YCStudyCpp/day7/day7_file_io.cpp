//
// Created by 杨充 on 2023/6/19.
//


//C++ 文件和流
//到目前为止，我们已经使用了 iostream 标准库，它提供了 cin 和 cout 方法分别用于从标准输入读取流和向标准输出写入流。

#include <fstream>
#include <iostream>
#include <fcntl.h>
#include <unistd.h>

using namespace std;


int main() {
    return 0;
}


void test2() {
    //fcntl.h头文件中的open函数是C语言中用于打开文件的函数。它提供了对文件的访问和操作的功能。
    //pathname：要打开的文件的路径名。
    //flags：打开文件的标志，用于指定文件的打开方式和操作选项。例如，O_RDONLY表示只读打开，O_WRONLY表示只写打开，O_RDWR表示读写打开，O_CREAT表示如果文件不存在则创建文件等。
    //mode：当使用O_CREAT标志创建新文件时，指定文件的权限。它是一个八进制数，例如0644表示文件权限为rw-r--r--。
    int buffer_fd = open("yc.txt", O_RDWR | O_CREAT, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
    cout << "buffer_fd " << buffer_fd << endl;
    //buffer_fd 4
    if (buffer_fd == -1) {
        //我们使用open函数打开名为"example.txt"的文件，以只写方式打开，并在需要时创建文件。如果打开文件失败，我们使用perror函数打印错误信息。
        perror("open");
    }
    // 写入文件
    const char *message = "Hello, World! doubi , i am yangchong";
    write(buffer_fd, message, strlen(message));
    // 关闭文件
    close(buffer_fd);
}