package com.dfjx.diy;

import com.tencent.taskrunner.CTSDBToHdfsRunner;
import com.tencent.taskrunner.ExportDataFromTimeSeriesDB;
import com.tencent.teg.dc.lhotse.newrunner.AbstractTaskRunner;
import com.tencent.teg.dc.lhotse.proto.LhotseObject;
import com.tencent.teg.dc.lhotse.runner.util.CommonUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

public class DataSyncRunner extends AbstractTaskRunner {
    public DataSyncRunner(String configFileName) {
        super(configFileName);
    }

    public static void main(String[] args) {

        String configureXml = "";
        if(args == null ||args.length < 1){
            System.out.println("需要配置文件作为输入参数!");
            System.exit(2);
        }else{
            configureXml = args[0];
        }

        //configureXml 自动生成，不用传入
        DataSyncRunner runner = new DataSyncRunner(configureXml);
        runner.startWork();
    }

    @Override
    public void execute() throws IOException {
        boolean success = false;

        try {
            taskRuntime = this.getTask();

            //获取用户在ui上填写的填写任务参数
            Map<String, String> params = taskRuntime.getProperties();
//            ExportDataFromTimeSeriesDB.ctsdbToHdfsTask(params);

            for (String key : params.keySet()){
                this.writeLocalLog(Level.INFO, "get "+key+"=" + params.get(key));
            }

//            ExportDataFromTimeSeriesDB.ctsdbToHdfsTask(params);

            DataSync dataSync = new DataSync();
            dataSync.doWork(params);

            success = true;
        } catch (Exception e) {
            this.writeLocalLog(Level.SEVERE ,"执行mpp to hdfs runner 出现异常");

            String st = CommonUtils.stackTraceToString(e);
            this.writeLocalLog(Level.SEVERE, "Exception stackTrace: " + st);

            commitTaskAndLog(LhotseObject.LState.RUNNING, "", "Exception: " + e.getMessage());
            throw new IOException(e);
        }finally {
            if (!success) {
                commitTaskAndLog(LhotseObject.LState.FAILED, "", "failed");
            } else {
                commitTaskAndLog(LhotseObject.LState.SUCCESSFUL, "", "success execute");

            }
        }
    }

    /**
     * 建议开发者，使用统一的停止方法
     * @throws IOException
     */
    @Override
    public void kill() throws IOException {
        this.writeLocalLog(Level.INFO, "mpp To hdfs Runner had been kill ");
        boolean killResult = false;
        try {
            killResult = CommonUtils.killProcess(this.taskRuntime, this);
            if (killResult) {
                this.writeLocalLog(Level.SEVERE, "kill job succeed!");
                this.commitTask(LhotseObject.LState.KILLED, "", "kill job succeed!");
            } else {
                this.writeLocalLog(Level.SEVERE, "kill job failed!");
                this.commitTask(LhotseObject.LState.HANGED, "", "kill job failed!");
            }
        } catch (Exception e) {
            this.writeLocalLog(Level.SEVERE,
                    "kill job failed:" + CommonUtils.stackTraceToString(e));
            this.commitTask(LhotseObject.LState.HANGED, "", "kill job failed!");
        }
    }

    private void commitTaskAndLog(LhotseObject.LState state, String runtimeId, String desc) {
        try {
            if (desc != null && desc.length() > 4000) {
                desc = StringUtils.substring(desc, 0, 4000);
            }
            this.commitTask(state, runtimeId, desc);
        }catch (Exception e) {
            String st = CommonUtils.stackTraceToString(e);
            this.writeLocalLog(Level.INFO, "Log_desc :" + desc);
            this.writeLocalLog(Level.SEVERE, "Commit task failed, StackTrace: " + st);
        }
    }
}
