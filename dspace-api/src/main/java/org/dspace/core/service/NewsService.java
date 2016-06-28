/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.core.service;

<<<<<<< HEAD
/**
 * Encapsulate access to the news texts.
 *
 * @author mhwood
 */
public interface NewsService {

    /**
     * Reads news from a text file.
     *
     * @param newsFile
     *        name of the news file to read in, relative to the news file path.
     * @return contents
     */
    public String readNewsFile(String newsFile);

    /**
     * Writes news to a text file.
     *
     * @param newsFile
     *        name of the news file to read in, relative to the news file path.
     * @param news
     *            the text to be written to the file.
     * @return string
     */
    public String writeNewsFile(String newsFile, String news);

    /**
     * Get the path for the news files.
     *
     * @return path
     */
    public String getNewsFilePath();

    /**
     * Check if the newsName is a valid one
     * 
     * @param newsName
     * @return true 
     * 				if the newsName is valid
     */
    public boolean validate(String newsName);
=======
public interface NewsService {
	boolean validate(String newsName);
>>>>>>> 88ed833e2cd8f0852b8c8f1f2fa5e419ea70b1a4
}
