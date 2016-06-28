/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.mediafilter;

<<<<<<< HEAD
import org.dspace.content.Item;

=======
>>>>>>> 88ed833e2cd8f0852b8c8f1f2fa5e419ea70b1a4
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class ImageMagickPdfThumbnailFilter extends ImageMagickThumbnailFilter {
   @Override
   public InputStream getDestinationStream(Item currentItem, InputStream source, boolean verbose)
        throws Exception
    {
		File f = inputStreamToTempFile(source, "impdfthumb", ".pdf");
		File f2 = null;
	    File f3 = null;
	    try
	    {
<<<<<<< HEAD
		    f2 = getImageFile(f, 0, verbose);
		    f3 = getThumbnailFile(f2, verbose);
=======
		    f2 = getImageFile(f, 0);
		    f3 = getThumbnailFile(f2);
>>>>>>> 88ed833e2cd8f0852b8c8f1f2fa5e419ea70b1a4
		    byte[] bytes = Files.readAllBytes(f3.toPath());
		    return new ByteArrayInputStream(bytes);
	    }
		finally
	    {
		    //noinspection ResultOfMethodCallIgnored
		    f.delete();
		    if (f2 != null)
		    {
			    //noinspection ResultOfMethodCallIgnored
			    f2.delete();
		    }
		    if (f3 != null)
		    {
			    //noinspection ResultOfMethodCallIgnored
			    f3.delete();
		    }
	    }
    }

   public static final String[] PDF = {"Adobe PDF"};
   @Override
   public String[] getInputMIMETypes()
  {
      return PDF;
  }

}
