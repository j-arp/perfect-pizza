package net.arpcentral.pizzadoh.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class HistoryTest {
    public static final String PREFS_NAME = "history";
    final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
    final Context context = Mockito.mock(Context.class);

    @Test
    public void constructs_history() throws Exception {
        Mockito.when(context.getSharedPreferences(PREFS_NAME, 0)).thenReturn(sharedPrefs);
        History history = new History(4, "thin", 150, 100, context);
        System.out.println("to delimited is " + history.toDelimited());
        assertEquals("4:thin:150:100", history.toDelimited(true));
    }

}