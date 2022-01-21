package com.xhd;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhd.mapper.UserMapper;
import com.xhd.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {

    //继承了BaseMapper，所有的方法都来自父类，我们也可以编写自己的扩展方法！
    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        //参数是一个wrapper ，条件构造器，这里我们先不用 null
        List<User> userList = userMapper.selectList(null);//查询全部的用户
        userList.forEach(System.out::println);
    }


    @Test//测试插入
    public void insertTest(){
        User user = new User();
        user.setName("wsk3");
        user.setAge(18);
        user.setEmail("2803708553@qq.com");
        Integer result = userMapper.insert(user); //会帮我们自动生成id
        System.out.println(result); //受影响的行数
        System.out.println(user); //通过日志发现id会自动回填
    }

    @Test//测试更新
    public void updateTest(){
        User user = new User();
        user.setId(1481878215397949446L);//怎么改id？？
        //通过条件自动拼接动态Sql
        user.setName("root");
        user.setAge(20);
        user.setEmail("root@qq.com");
        int i = userMapper.updateById(user);//updateById，但是参数是个user
        System.out.println(i);
    }

    @Test//测试乐观锁成功
    public void testOptimisticLocker1(){
        //1、查询用户信息
        User user = userMapper.selectById(1L);
        //2、修改用户信息
        user.setAge(18);
        user.setEmail("2803708553@qq.com");
        //3、执行更新操作
        userMapper.updateById(user);
    }

    @Test//测试乐观锁失败  多线程下
    public void testOptimisticLocker2(){
        //线程1
        User user1 = userMapper.selectById(1L);
        user1.setAge(1);
        user1.setEmail("2803708553@qq.com");
        //模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1L);
        user2.setAge(2);
        user2.setEmail("2803708553@qq.com");
        userMapper.updateById(user2);
        //自旋锁来多次尝试提交！
        userMapper.updateById(user1);//如果没有乐观锁就会覆盖插队线程的值1122
    }


    @Test//通过id查询单个用户
    public void testSelectById(){
        User user = userMapper.selectById(2L);
        System.out.println(user);

    }

    @Test//通过id查询多个用户
    public void testSelectBatchIds(){
        ArrayList<String> list = new ArrayList<>();
        list.add("1L");
        list.add("2L");

        List<User> users = userMapper.selectBatchIds(list);
//        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
        users.forEach(System.out::println);
        //System.out.println(users);
    }

    @Test//通过条件查询之一  map
    public void testMap(){
        HashMap<String, Object> map = new HashMap<>();
        //自定义要查询的
        map.put("name","wsk");
        map.put("age",18);
        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            System.out.println(user);
        }
//        users.forEach(System.out::println);
    }

    /**
     *  page.getRecords(): 获取分页后的数据
     *  page.getTotal():一共多少跳数据
     *  page.getPages：一共多少页
     *  page.getCurrent:当前第几页
     */
    @Test//测试分页查询
    public void testPage(){
        //参数一current：当前页   参数二size：页面大小
        //使用了分页插件之后，所有的分页操作都变得简单了
        Page<User> page = new Page<>(3,5);
        userMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);
        System.out.println("总页数==>"+page.getTotal());
    }

    @Test
    public void testDeleteById(){
        userMapper.deleteById(1L);

    }

    @Test
    public void testDeleteBatchIds(){
        userMapper.deleteBatchIds(Arrays.asList(1481878215397949448L,1481878215397949449L));
    }
    @Test
    public void testD() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("age", "18");
        map.put("name", "lol");
        userMapper.deleteByMap(map);

        System.out.println("hot-fix test");
    }
}
