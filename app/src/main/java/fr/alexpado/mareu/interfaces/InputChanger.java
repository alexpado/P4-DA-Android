package fr.alexpado.mareu.interfaces;

import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;

/**
 * Interface allowing to simplify the use of {@link TextWatcher} when the goal of the listener is
 * just to retrieve the final {@link Editable} text content.
 *
 * @see #afterTextChanged(Editable)
 * @see #onTextChanged(CharSequence, int, int, int)
 */
public interface InputChanger extends TextWatcher {

    /**
     * This method is called to notify you that, within <code>s</code>, the <code>count</code>
     * characters beginning at <code>start</code> are about to be replaced by new text with length
     * <code>after</code>. It is an error to attempt to make changes to <code>s</code> from this
     * callback.
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * This method is called to notify you that, within <code>s</code>, the <code>count</code>
     * characters beginning at <code>start</code> have just replaced old text that had length
     * <code>before</code>. It is an error to attempt to make changes to <code>s</code> from this
     * callback.
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from this callback, but be careful
     * not to get yourself into an infinite loop, because any changes you make will cause this
     * method to be called again recursively. (You are not told where the change took place because
     * other afterTextChanged() methods may already have made other changes and invalidated the
     * offsets.  But if you need to know here, you can use {@link Spannable#setSpan} in
     * {@link #onTextChanged} to mark your place and then look up from here where the span ended
     * up.
     *
     * @param s
     */
    @Override
    default void afterTextChanged(Editable s) {

        this.onTextChanged(s.toString());
    }

    void onTextChanged(String str);

}
