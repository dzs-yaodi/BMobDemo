package com.xw.bmobdemo;

import android.text.TextUtils;

import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BmobUtils {

    public static BmobUtils instance = null;

    public static BmobUtils getInstance() {
        if (instance == null) {
            instance = new BmobUtils();
        }

        return instance;
    }

    /**
     * 保存单条数据
     *
     * @param classType
     * @param <T>
     */
    public <T extends BmobObject> void saveOneObject(T classType, final BmobResponce responce) {

        classType.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    responce.onSucc("添加成功");
                } else {
                    responce.onFail("添加失败,错误码：" + e.toString());
                }
            }
        });
    }

    /**
     * 批量添加
     *
     * @param list
     * @param responce
     */
    public void saveList(List<BmobObject> list, final BmobResponce responce) {

        new BmobBatch().insertBatch(list).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    String msg = "";
                    for (int i = 0; i < list.size(); i++) {
                        BmobException e1 = list.get(i).getError();
                        if (e1 != null) {
                            msg = msg + "第" + i + "条,";
                        }
                    }

                    if (!TextUtils.isEmpty(msg)) {
                        responce.onFail(msg + "添加失败");
                    } else {
                        responce.onSucc("批量添加成功");
                    }
                }else{
                    responce.onFail("批量添加失败");
                }
            }
        });
    }

    /**
     * 修改单条数据
     *
     * @param classType
     * @param id
     * @param responce
     * @param <T>
     */
    public <T extends BmobObject> void updataOne(T classType, String id, final BmobResponce responce) {
        classType.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    responce.onSucc("修改成功");
                } else {
                    responce.onFail("修改失败,错误码：" + e.toString());
                }
            }
        });
    }

    /**
     * 批量更新
     * @param bmobObjects
     * @param responce
     */
    public void updataList(List<BmobObject> bmobObjects, final BmobResponce responce) {
        new BmobBatch().updateBatch(bmobObjects).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    String msg = "";
                    for (int i = 0; i < list.size(); i++) {
                        BmobException e1 = list.get(i).getError();
                        if (e1 != null) {
                            msg = msg + "第" + i + "条,";
                        }
                    }

                    if (!TextUtils.isEmpty(msg)) {
                        responce.onFail(msg + "修改失败");
                    } else {
                        responce.onSucc("批量修改成功");
                    }
                }else{
                    responce.onFail("批量修改失败");
                }
            }
        });
    }

    /**
     * 删除单条数据
     *
     * @param classType
     * @param id
     * @param responce
     * @param <T>
     */
    public <T extends BmobObject> void deletOne(T classType, String id, final BmobResponce responce) {
        classType.delete(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    responce.onSucc("删除成功");
                } else {
                    responce.onFail("删除失败,错误码：" + e.toString());
                }
            }
        });
    }

    /**
     * 批量删除
     * @param objectList
     * @param responce
     */
    public void deletList(List<BmobObject> objectList,final BmobResponce responce){
        new BmobBatch().deleteBatch(objectList).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    String msg = "";
                    for (int i = 0; i < list.size(); i++) {
                        BmobException e1 = list.get(i).getError();
                        if (e1 != null) {
                            msg = msg + "第" + i + "条,";
                        }
                    }

                    if (!TextUtils.isEmpty(msg)) {
                        responce.onFail(msg + "删除失败");
                    } else {
                        responce.onSucc("批量删除成功");
                    }
                }else{
                    responce.onFail("批量删除失败");
                }
            }
        });
    }

    /*********************************************一下方法有误，待封装******************************************/
    /**
     * 查询单条数据(不能用，在界面中直接使用一下代码);
     *
     * @param bmobQuery
     * @param id
     * @param responce
     * @param <T>
     */
    public <T extends BmobObject> void findOne(BmobQuery<T> bmobQuery, String id, final BmobResponce responce) {
        bmobQuery.getObject(id, new QueryListener<T>() {
            @Override
            public void done(T t, BmobException e) {

                if (e == null) {
                    responce.onSucc(t);
                } else {
                    responce.onFail("查询失败");
                }
            }
        });
    }

    /**
     * 查询多条数据(不能用，在界面中直接使用一下代码);
     *
     * @param bmobQuery
     * @param responce
     * @param <T>
     */
    public <T extends BmobObject> void findList(BmobQuery<T> bmobQuery, final BmobResponce responce) {

        bmobQuery.findObjects(new FindListener<T>() {
            @Override
            public void done(List<T> list, BmobException e) {
                if (e == null) {
                    responce.onSucc(list);
                } else {
                    responce.onFail("查询失败");
                }
            }
        });
    }
}
