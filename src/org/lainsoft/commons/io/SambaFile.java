package org.lainsoft.commons.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileFilter;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import jcifs.smb.SmbFilenameFilter;


public class SambaFile implements FileWrapper, Serializable {
	
	private static final long serialVersionUID = 3689732554716964877L;
	
	private SmbFile inner; 
	
	public SambaFile (SmbFile context, String name) throws MalformedURLException, UnknownHostException {
		inner = new SmbFile(context, name);
	}
	public SambaFile (SmbFile context, String name, int shareAccess) throws MalformedURLException, UnknownHostException {
		inner = new SmbFile(context, name, shareAccess);
	}
	public SambaFile (String url) throws MalformedURLException {
		inner = new SmbFile(url);
	}
	public SambaFile (String url, NtlmPasswordAuthentication auth) throws MalformedURLException {
		inner = new SmbFile(url, auth);
	}
	public SambaFile (String url, NtlmPasswordAuthentication auth, int shareAccess) throws MalformedURLException {
		inner = new SmbFile(url, auth, shareAccess);
	}
	public SambaFile (String context, String name) throws MalformedURLException {
		inner = new SmbFile(context, name);
	}
	public SambaFile (String context, String name, NtlmPasswordAuthentication auth) throws MalformedURLException {
		inner = new SmbFile(context, name, auth);
	}
	public SambaFile (String context, String name, NtlmPasswordAuthentication auth, int shareAccess) throws MalformedURLException {
		inner = new SmbFile(context, name, auth, shareAccess);
	}
	public SambaFile (java.net.URL url) {
		inner = new SmbFile(url);
	}
	public SambaFile (java.net.URL url, NtlmPasswordAuthentication auth) {
		inner = new SmbFile( url, auth);
	}
	
	public SambaFile(SmbFile f) {
		inner = f;
	}
	public boolean createNewFile() {
		try {
			inner.createNewFile();
		} catch (SmbException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public List<SambaFile> listFiles() {
		List <SambaFile> lFiles = null; 
		try{
			SmbFile[] files = inner.listFiles();
			if (files != null){
				lFiles = new ArrayList<SambaFile>();
				for(SmbFile f : files){
					lFiles.add(new SambaFile(f));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return lFiles;
	}

	public boolean canRead() throws Exception {
		return inner.canRead();
	}

	public boolean canWrite() throws Exception {
		return inner.canWrite();
	}

	public boolean delete() {
		try {
			inner.delete();
		} catch (SmbException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean exists() {
		try {
			return inner.exists();
		} catch (SmbException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getCanonicalPath(){
		return inner.getCanonicalPath();
	}

	public String getName() {
		return inner.getName();
	}

	public String getParent() {
		return inner.getParent();
	}

	public String getPath() {
		return inner.getPath();
	}

	public boolean isDirectory() {
		
		try {
			return inner.isDirectory();
		} catch (SmbException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isFile() {
		try{
			return inner.isFile();
		}catch(SmbException e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean isHidden() 
	throws Exception {
		return inner.isHidden();
	}

	public long lastModified(){
		try {
			return inner.lastModified();
		} catch (SmbException e) {			
			e.printStackTrace();
			return 0;
		}
	}

	public long length() 
	throws Exception {
		return inner.length();
	}

	public String[] list() 
	throws Exception{
		return inner.list();
	}

	public boolean mkdir() {
		try {
			inner.mkdir();
		} catch (SmbException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean mkdirs() {
		try {
			inner.mkdirs();
		} catch (SmbException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setLastModified(long time) {
		try {
			inner.setLastModified(time);
		} catch (SmbException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setReadOnly() {
		try {
			inner.setReadOnly();
		} catch (SmbException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public URL toURL() throws MalformedURLException {
		return inner.toURL();
	}

	@SuppressWarnings("unchecked")
	public <E> E getInner() {
		return (E)inner;
	}
	public List<SambaFile> listFiles(final FileFilterWrapper wrapper) {
	
		SmbFile files[];
		try {
			files = inner.listFiles(new SmbFileFilter() {				
				public boolean accept(SmbFile f) throws SmbException {
					return wrapper.accept(new SambaFile(f));
				}
			});
		} catch (SmbException e) {
			e.printStackTrace();
			return null;
		}
		if(files != null){
			List<SambaFile> lfiles = new ArrayList<SambaFile>();
			for (SmbFile f : files){
				lfiles.add(new SambaFile(f));
			}
			return lfiles;
		}
		return null;
	}

	public String[] list(final FileNameFilterWrapper filter) {
		try {
			return inner.list(new SmbFilenameFilter() {
				public boolean accept(SmbFile dir, String name) {
					return filter.accept(new SambaFile(dir), name);
				}
			});
		} catch (SmbException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<SambaFile> listFiles(final FileNameFilterWrapper filter) {
		SmbFile[] files = null;
		try {
			files = inner.listFiles(new SmbFilenameFilter() {
				public boolean accept(SmbFile dir, String name) {
					return filter.accept(new SambaFile(dir), name);
				}
			});
		} catch (SmbException e) {
			e.printStackTrace();
		}
		if(files != null){
			List<SambaFile> lfiles = new ArrayList<SambaFile>();
			for (SmbFile f : files){
				lfiles.add(new SambaFile(f));
			}
			return lfiles;
		}
		return null;
	}

	@Override
	public boolean equals(Object arg0) {
		return inner.equals(arg0);
	}
	
	@Override
	public int hashCode() {
		return inner.hashCode();
	}

	public InputStream getInputStream() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(inner.getInputStream());
		return new ByteArrayInputStream(out.toByteArray());
	}
	public OutputStream getOutputStream() throws IOException{
		return new SmbFileOutputStream(inner);
	}
	
	public String
	toString(){
		return inner.toString();
	}
	

}
