package com.mwee.xdroid.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.mwee.xdroid.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import butterknife.BindView;
import cn.droidlover.xdroid.base.XActivity;
import cn.droidlover.xdroidbase.router.Router;

/**
 * Created by zhangmin on 2017/9/19.
 */

public class TestActivity extends XActivity {
    @BindView(R.id.tvTest)
    TextView tvTest;

    @Override
    public void initData(Bundle savedInstanceState) {

        //构造数据


        //得到数据
        StringBuilder stringBuilder = new StringBuilder();

        Class<?> cls = TestRuntimeAnnotation.class;

        ClassInfo classInfo = cls.getAnnotation(ClassInfo.class);
        // 获取指定类型的注解
        stringBuilder.append("Class注解：").append("\n");
        if (classInfo != null) {
            stringBuilder.append(Modifier.toString(cls.getModifiers())).append(" ")
                    .append(cls.getSimpleName()).append("\n");
            stringBuilder.append("注解值: ").append(classInfo.value()).append("\n\n");
        }


        stringBuilder.append("Field注解：").append("\n");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            FieldInfo fieldInfo = field.getAnnotation(FieldInfo.class);
            if (fieldInfo != null) {
                stringBuilder.append(Modifier.toString(field.getModifiers())).append(" ")
                        .append("  field.getType().getSimpleName()-->" + field.getType().getSimpleName())
                        .append("  field.getName()-->" + field.getName());
                stringBuilder.append("  注解值1:").append(fieldInfo.value());
                stringBuilder.append("  注解值2: ").append(Arrays.toString(fieldInfo.value()))
                        .append("\n\n");
            }
        }


        stringBuilder.append("methord注释: ").append("\n");
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            MethodInfo MethodInfo = method.getAnnotation(MethodInfo.class);
            if (MethodInfo != null) {
                stringBuilder.append(Modifier.toString(method.getModifiers())).append(" ")
                        .append("  method.getName()-->" + method.getName())
                        .append("  method.getReturnType().getSimpleName()-->" + method.getReturnType().getSimpleName());
                stringBuilder.append("注解值:").append("age-->" + MethodInfo.age())
                        .append("  data-->" + MethodInfo.data())
                        .append("  name-->" + MethodInfo.name())
                        .append("\n\n");
            }
        }

        stringBuilder.append("methord参数注释: ").append("\n");
        Method[] methodParams = cls.getMethods();
        for (Method method : methodParams) {
            Annotation[][] annotations = method.getParameterAnnotations();
            for (Annotation[] temps : annotations) {
                for (Annotation temp : temps) {
                    if (temp instanceof SF) {
                        stringBuilder.append(((SF) temp).value());
                    }
                }

            }
        }


        //展示数据
        tvTest.setText(stringBuilder);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }


    @ClassInfo("TestRuntimeAnnotation")
    class TestRuntimeAnnotation {

        @FieldInfo(value = {1, 2})
        int i;

        @FieldInfo(value = {10086})
        String fieidInfo = "";


        @MethodInfo(name = "zm", data = "2017")
        public String getMethordInfo() {
            return TestRuntimeAnnotation.class.getSimpleName();

        }

        @MethodInfo(age = 10, name = "xiaogui", data = "2000")
        public String getMethordInfoparamers(@SF("zuoai") String zuoai, @SF("cuopi") String cuopi) {
            return TestRuntimeAnnotation.class.getSimpleName();
        }


    }


    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(TestActivity.class)
                .launch();
    }


}
