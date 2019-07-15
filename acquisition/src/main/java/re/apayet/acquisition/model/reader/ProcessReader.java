package re.apayet.acquisition.model.reader;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import re.apayet.acquisition.model.base.abstraite.Reader;
import re.apayet.acquisition.model.data.ActionKey;
import re.apayet.acquisition.model.data.ProcessData;

import java.util.ArrayList;
import java.util.List;

import static re.apayet.acquisition.model.reader.ProcessReader.Kernel32.*;
import static re.apayet.acquisition.model.reader.ProcessReader.Psapi.GetModuleBaseNameW;
import static re.apayet.acquisition.model.reader.ProcessReader.User32DLL.*;


public class ProcessReader extends Reader<ProcessData> implements NativeKeyListener, NativeMouseListener {

  private static final int MAX_TITLE_LENGTH = 1024;
  
  private static Logger LOG = LogManager.getLogger(ProcessReader.class.getName());

  @Override public void nativeKeyPressed(NativeKeyEvent input) {
    addEvent(input, ActionKey.KEY_ACTION);
    LOG.trace("nativeKeyPressed");   
  }

  @Override
  public void nativeMouseClicked(NativeMouseEvent input) {
    LOG.trace("nativeMouseClicked");
    addEvent(input, ActionKey.MOUSE_ACTION);
  }

  @Override public void nativeKeyTyped(NativeKeyEvent input)        { LOG.trace("Not used : nativeKeyTyped");}
  @Override public void nativeKeyReleased(NativeKeyEvent input)     { LOG.trace("Not used : nativeKeyReleased");  }
  @Override public void nativeMousePressed(NativeMouseEvent input)  { LOG.trace("Not used : nativeMousePressed"); }
  @Override public void nativeMouseReleased(NativeMouseEvent input) { LOG.trace("Not used : nativeMouseReleased");}

  public void addEvent(NativeKeyEvent input, ActionKey action) {
    if (isWork()) {
      addElements(action);
    }
  }

  public void addEvent(NativeMouseEvent input, ActionKey action) {
    if (isWork()) {
      addElements(action);
    }
  }

  private void addElements(ActionKey action) {
    Long time = System.currentTimeMillis();
    for (String res : processCapture()) {
      LOG.debug(action + " : Process added [ " + action + " ] - [ When : " + time + " ]");
      addData(new ProcessData(res, time, action));
    }
  }

  private List<String> processCapture() {

    String activeWindowTitle;
    String activeWindowProcess;
    List<String> result = new ArrayList<>();

    char[] buffer = new char[MAX_TITLE_LENGTH * 2];
    GetWindowTextW(GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
    activeWindowTitle = Native.toString(buffer);
    LOG.trace("Active window title: " + activeWindowTitle );

    PointerByReference pointer = new PointerByReference();
    GetWindowThreadProcessId(GetForegroundWindow(), pointer);
    Pointer process = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pointer.getValue());
    GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
    activeWindowProcess = Native.toString(buffer);
    LOG.trace("Active window process: " + activeWindowProcess);

    result.add(activeWindowTitle);
    result.add(activeWindowProcess);
    return result;
  }
  
  static class Psapi {
    static { Native.register("psapi"); }
    public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
}

static class Kernel32 {
    static { Native.register("kernel32"); }
    public static int PROCESS_QUERY_INFORMATION = 0x0400;
    public static int PROCESS_VM_READ = 0x0010;
    public static native int GetLastError();
    public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
}

static class User32DLL {
    static { Native.register("user32"); }
    public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
    public static native HWND GetForegroundWindow();
    public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
}
}
