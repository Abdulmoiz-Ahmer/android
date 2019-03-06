/*
 * Nextcloud Android client application
 *
 * @author Chris Narkiewicz
 * Copyright (C) 2019 Chris Narkiewicz, EZ Aquarii
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.nextcloud.client.preferences;

import com.owncloud.android.datamodel.OCFile;
import com.owncloud.android.utils.FileSortOrder;

public interface AppPreferences {
    boolean instantPictureUploadEnabled();
    boolean instantVideoUploadEnabled();

    /**
     * Get preferred folder sort order.
     *
     * @return sort order     the sort order, default is {@link FileSortOrder#sort_a_to_z} (sort by name)
     */
    FileSortOrder getSortOrderByFolder(OCFile folder);

    /**
     * Set preferred folder sort order.
     *
     * @param sortOrder the sort order
     */
    void setSortOrder(OCFile folder, FileSortOrder sortOrder);

    FileSortOrder getSortOrderByType(FileSortOrder.Type type);

    /**
     * Get preferred folder sort order.
     *
     * @return sort order     the sort order, default is {@link FileSortOrder#sort_a_to_z} (sort by name)
     */
    FileSortOrder getSortOrderByType(FileSortOrder.Type type, FileSortOrder defaultOrder);

    /**
     * Set preferred folder sort order.
     *
     * @param sortOrder the sort order
     */
    void setSortOrder(FileSortOrder.Type type, FileSortOrder sortOrder);

    void setShowDetailedTimestampEnabled(boolean showDetailedTimestamp);
    boolean isShowDetailedTimestampEnabled();

    boolean isShowMediaScanNotifications();
    void setShowMediaScanNotifications(boolean showMediaScanNotification);

    float getGridColumns();
    void setGridColumns(float gridColumns);

    void removeLegacyPreferences();
}
