package org.lainsoft.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import jcifs.smb.SmbException;


public interface FileWrapper{
    public boolean canRead() throws Exception;
    public boolean canWrite() throws Exception;
    public boolean createNewFile()throws IOException, SmbException;
    public boolean delete();
    public boolean equals(Object obj);
    public boolean exists();
    public String getCanonicalPath();
    public String getName();
    public String getParent();
    public String getPath();
    public int hashCode();
    public boolean isDirectory();
    public boolean isFile();
    public boolean isHidden() throws Exception;
    public long lastModified();
    public long length() throws Exception;
    public String[] list() throws Exception;
    String[] list(FileNameFilterWrapper filter);
    public List<? extends FileWrapper> listFiles();
    public List<? extends FileWrapper> listFiles(FileFilterWrapper wrapper) ;
    public List<? extends FileWrapper> listFiles(FileNameFilterWrapper filter) ;
    public boolean mkdir();
    public boolean mkdirs();
    public boolean setLastModified(long time) ;
    public boolean setReadOnly();
    public String toString();
    public URL toURL()throws MalformedURLException;
    public <E> E getInner();
    public InputStream getInputStream() throws IOException;
    public OutputStream getOutputStream() throws IOException;
}


