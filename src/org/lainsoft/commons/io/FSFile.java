package org.lainsoft.commons.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FSFile implements FileWrapper, Serializable {
	private File inner;
	private static final long serialVersionUID = 4279263979802631721L;

	public FSFile(File parent, String child) {
		inner = new File(parent, child);
	}

	public FSFile(String pathname) {
		inner = new File(pathname);
	}

	public FSFile(String parent, String child) {
		inner = new File(parent, child);
	}

	public FSFile(URI uri) {
		inner = new File(uri);
	}

	public FSFile(File file) {
		inner = file;
	}

	public boolean createNewFile() throws IOException {
		return inner.createNewFile();
	}

	public List<FSFile> listFiles() {
		File[] files = inner.listFiles();
		List<FSFile> lFiles = null;
		if (files != null) {
			lFiles = new ArrayList<FSFile>();
			for (File f : files) {
				lFiles.add(new FSFile(f));
			}
		}
		return lFiles;
	}

	public boolean canRead() {
		return inner.canRead();
	}

	public boolean canWrite() {
		return inner.canWrite();
	}

	public boolean delete() {
		return inner.delete();
	}

	public boolean exists() {
		return inner.exists();
	}

	public String getCanonicalPath(){
		try {
			return inner.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
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
		return inner.isDirectory();
	}

	public boolean isFile() {
		return inner.isFile();
	}

	public boolean isHidden() {
		return inner.isHidden();
	}

	public long lastModified() {
		return inner.lastModified();
	}

	public long length() {
		return inner.length();
	}

	public String[] list() {
		return inner.list();
	}

	public boolean mkdir() {
		return inner.mkdir();
	}

	public boolean mkdirs() {
		return inner.mkdirs();
	}

	public boolean setLastModified(long time) {
		return inner.setLastModified(time);
	}

	public boolean setReadOnly() {
		return inner.setReadOnly();
	}

	public URL toURL() throws MalformedURLException {
		return inner.toURL();
	}

	@SuppressWarnings("unchecked")
	public <E> E getInner() {
		return (E) inner;
	}

	public List<FSFile> listFiles(final FileFilterWrapper wrapper) {

		File files[] = inner.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return wrapper.accept(new FSFile(pathname));
			}
		});
		if (files != null) {
			List<FSFile> lfiles = new ArrayList<FSFile>();
			for (File f : files) {
				lfiles.add(new FSFile(f));
			}
			return lfiles;
		}
		return null;
	}

	public String[] list(final FileNameFilterWrapper filter) {
		return inner.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return filter.accept(new FSFile(dir), name);
			}
		});
	}

	public List<FSFile> listFiles(final FileNameFilterWrapper filter) {
		File[] files = inner.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return filter.accept(new FSFile(dir), name);
			}
		});
		if (files != null) {
			List<FSFile> lfiles = new ArrayList<FSFile>();
			for (File f : files) {
				lfiles.add(new FSFile(f));
			}
			return lfiles;
		}
		return null;
	}

	public InputStream getInputStream() throws IOException {
		return new FileInputStream(inner);
	}

	public OutputStream getOutputStream() throws IOException {		
		return new FileOutputStream(inner);
	}

	@Override
	public boolean equals(Object arg0) {
		return inner.equals(arg0);
	}

	@Override
	public int hashCode() {
		return inner.hashCode();
	}

	public String toString() {
		return inner.toString();
	}
}
