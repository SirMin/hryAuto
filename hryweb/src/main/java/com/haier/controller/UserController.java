package com.haier.controller;

import com.haier.enums.StatusCodeEnum;
import com.haier.exception.HryException;
import com.haier.po.User;
import com.haier.response.Result;
import com.haier.service.UserService;
import com.haier.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: luqiwei
 * @Date: 2018/5/12 15:11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    //按主键查询用户
    @PostMapping("/selectOne.do")
    public Result selectOne(Integer id){
        return ResultUtil.success(userService.selectOne(id));
    }

    //新增用户
    @PostMapping("/insertOne.do")
    public Result insertOne(User user){
        return ResultUtil.success(userService.insertOne(user));
    }

    //更新用户
    @PostMapping("/updateOne.do")
    public Result updateOne(User user){
        if(user==null||user.getId()==null){
            throw new HryException(StatusCodeEnum.PARAMETER_ERROR);
        }
        return ResultUtil.success(userService.updateOne(user.getId(),user));
    }

    //删除用户
    @PostMapping("/deleteOne.do")
    public Result deleteOne(Integer id){
        return ResultUtil.success(userService.deleteOne(id));
    }

    //综合查询
    @PostMapping("/selectByCondition.do")
    public Result selectByCondition(User user,Integer pageNum,Integer pageSize){
        return ResultUtil.success(userService.selectByCondition(user,pageNum,pageSize));
    }

    //登录
    @PostMapping("/login.do")
    public Result login(HttpServletRequest request,HttpServletResponse response, String identity, String password){
        HttpSession session;
        User user = userService.findUser(identity, password);
        if(user==null){
            return ResultUtil.error(StatusCodeEnum.LOGIN_ERROR);
        }
        else{
            session=request.getSession(true);
            session.setMaxInactiveInterval(60*60*24*30);//秒为单位,设置一个月的有效时间
            session.setAttribute("user",user.getIdentity());
            //设置cookie信息
            Cookie cookie=new Cookie("userCookie",
                    "identity="+user.getIdentity()+"&realname="
                            +user.getRealname()+"&groupid="+user.getGroupid());
            cookie.setPath("/");
            cookie.setMaxAge(Integer.MAX_VALUE);//设置cookie永不过期.setMaxAge单位为秒
            response.addCookie(cookie);
            return ResultUtil.success();
        }
    }

    //登出
    @PostMapping("/logout")
    public Result logout(HttpSession session){
        //清除session即可
        session.invalidate();
        return ResultUtil.success();
    }

}
