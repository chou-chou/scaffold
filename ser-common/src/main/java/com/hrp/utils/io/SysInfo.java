package com.hrp.utils.io;

import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;

import java.io.*;
import java.util.Scanner;

/**
 * SysInfo
 *
 * @author KVLT
 * @date 2017-04-17.
 */
public class SysInfo {

    private static Sigar sigar = null;

    /**
     * 获取当前机器的MAC地址
     *
     * @return
     */
    public static String getMac() {
        sigar = new Sigar();
        try {
            String[] ifaces = sigar.getNetInterfaceList();
            String hwaddr = null;
            for (int i = 0; i < ifaces.length; i++) {
                NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
                if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
                        || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                        || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())
                        || "0.0.0.0".equals(cfg.getNetmask())) {
                    continue;
                }

                if ("255.255.255.0".equals(cfg.getNetmask())) {
                    hwaddr = cfg.getHwaddr();
                    break;
                }
            }

            return hwaddr != null ? hwaddr : null;
        } catch (Exception e) {
            return null;
        } finally {
            if (sigar != null)
                sigar.close();
        }
    }

    public static String getCPU() {
        Process process;
        String serial = null;
        Scanner sc = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            sc = new Scanner(process.getInputStream());
            String property = sc.next();
            serial = sc.next();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sc != null)
                sc.close();
        }
        return serial;
    }

    private static String getHDSerial(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("tmp_02", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n" + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
        }
        if (result.trim().length() < 1 || result == null) {
            result = "";
        }
        return result.trim();
    }

    private static String getCPUSerial() {
        String result = "";
        try {
            File file = File.createTempFile("tmp_01", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "On Error Resume Next \r\n\r\n" + "strComputer = \".\"  \r\n"
                    + "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n"
                    + "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n"
                    + "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n "
                    + "For Each objItem in colItems\r\n " + "    Wscript.Echo objItem.ProcessorId  \r\n "
                    + "    exit for  ' do the first cpu only! \r\n" + "Next                    ";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if (result.trim().length() < 1 || result == null) {
            result = "";
        }
        return result.trim();
    }

    public static void getEthernetInfo() {
        Sigar sigar = null;
        try {
            sigar = new Sigar();

            String[] ifaces = sigar.getNetInterfaceList();

            for (int i = 0; i < ifaces.length; i++) {

                NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);

                if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())

                        || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0

                        || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())

                        || "0.0.0.0".equals(cfg.getNetmask())) {

                    continue;

                }

                System.out.println("IP地址：" + cfg.getAddress());// IP地址

                System.out.println("网关： " + cfg.getBroadcast());// 网关广播地址

                System.out.println("MAC地址： " + cfg.getHwaddr());// 网卡MAC地址

                System.out.println("子网掩码： " + cfg.getNetmask());// 子网掩码

                System.out.println("描述： " + cfg.getDescription());// 网卡描述信息

                System.out.println("类型： " + cfg.getType());//

                System.out.println("cfg.getDestination() = " + cfg.getDestination());

                System.out.println("cfg.getFlags() = " + cfg.getFlags());//

                System.out.println("cfg.getMetric() = " + cfg.getMetric());

                System.out.println("cfg.getMtu() = " + cfg.getMtu());

                System.out.println("cfg.getName() = " + cfg.getName());

                System.out.println();

            }

        } catch (Exception e) {

            System.out.println("Error while creating GUID" + e);

        } finally {

            if (sigar != null)

                sigar.close();

        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
        System.out.println(getCPU());
        System.out.println(getHDSerial("C:/"));
        System.out.println(getMac());

        //getEthernetInfo();
    }

}
