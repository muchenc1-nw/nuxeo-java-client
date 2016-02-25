/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *         Vladimir Pasquier <vpasquier@nuxeo.com>
 */
package org.nuxeo.java.client.api.objects.blob;

import java.io.File;

import org.nuxeo.java.client.api.ConstantsV1;
import org.nuxeo.java.client.api.objects.NuxeoEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @since 0.1
 */
public class Blob extends NuxeoEntity {

    @JsonIgnore
    protected String mimeType;

    @JsonIgnore
    protected String fileName;

    @JsonIgnore
    protected File file;

    public Blob() {
        super(ConstantsV1.ENTITY_TYPE_BLOB);
    }

    public Blob(String fileName) {
        this(fileName, null);
    }

    public Blob(File file) {
        this(file.getName(), ConstantsV1.APPLICATION_OCTET_STREAM);
        this.file = file;
    }

    public Blob(String fileName, String mimeType) {
        super(null);
        this.fileName = fileName;
        this.file = null;
        setMimeType(mimeType);
    }

    @JsonIgnore
    public int getLength() {
        long length = file.length();
        if (length > (long) Integer.MAX_VALUE) {
            return -1;
        }
        return (int) length;
    }

    public File getFile() {
        return file;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType == null ? ConstantsV1.APPLICATION_OCTET_STREAM : mimeType;
    }

    @JsonIgnore
    protected String formatLength(int len) {
        int k = len / 1024;
        if (k <= 0) {
            return len + " B";
        } else if (k < 1024) {
            return k + " K";
        } else {
            return (k / 1024) + " M";
        }
    }

    @Override
    public String toString() {
        return fileName + " - " + mimeType + " - " + formatLength(getLength());
    }
}
