package com.haier.service;

import com.haier.po.*;

import java.util.List;

/**
 * @Description:
 * @Author: luqiwei
 * @Date: 2018/6/26 16:21
 */
public interface RunService {
    Tservice getTservice(Integer serviceId);

    Tenv getTenv(Integer envId);

    Tservicedetail getTservicedetail(Integer serviceId, Integer envId);

    Ti getTi(Integer serviceId, String uri);

    List<Tcase> getTcase(Integer iId, Integer envId, String caseDesigner);
}
