/*
 * $Id$
 *
 * MinifyMojo Maven Plugin
 * http://code.google.com/p/maven-samaxes-plugin/
 *
 * Copyright (c) 2009 samaxes.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.samaxes.maven.plugin.minify;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.apache.maven.plugin.logging.Log;

import com.yahoo.platform.yui.compressor.CssCompressor;

/**
 * Task for merging and compressing CSS files.
 */
public class ProcessCSSFilesTask extends ProcessFilesTask {

    /**
     * Task constructor.
     * 
     * @param log Maven plugin log
     * @param bufferSize size of the buffer used to read source files.
     * @param webappSourceDir web resources source directory
     * @param webappTargetDir web resources target directory
     * @param inputDir directory containing source files
     * @param sourceFiles list of source files to include
     * @param sourceIncludes list of source files to include
     * @param sourceExcludes list of source files to exclude
     * @param outputDir directory to write the final file
     * @param finalFilename final filename
     * @param suffix final filename suffix
     * @param charset if a character set is specified, a byte-to-char variant allows the encoding to be selected.
     *        Otherwise, only byte-to-byte operations are used
     * @param linebreak split long lines after a specific column
     */
    public ProcessCSSFilesTask(Log log, Integer bufferSize, String webappSourceDir, String webappTargetDir,
            String inputDir, List<String> sourceFiles, List<String> sourceIncludes, List<String> sourceExcludes,
            String outputDir, String finalFilename, String suffix, String charset, int linebreak) {
        super(log, bufferSize, webappSourceDir, webappTargetDir, inputDir, sourceFiles, sourceIncludes, sourceExcludes,
                outputDir, finalFilename, suffix, charset, linebreak);
    }

    /**
     * Minifies CSS file.
     */
    protected void minify() {
        if (minifiedFile != null) {
            try {
                log.info("Creating minified file [" + minifiedFile.getName() + "]");
                Reader reader = new FileReader(mergedFile);
                Writer writer = new FileWriter(minifiedFile);
                CssCompressor compressor = new CssCompressor(reader);

                compressor.compress(writer, linebreak);
                reader.close();
                writer.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
