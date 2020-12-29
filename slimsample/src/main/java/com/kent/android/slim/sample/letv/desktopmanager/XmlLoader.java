package com.kent.android.slim.sample.letv.desktopmanager;

import com.kent.android.slim.sample.letv.desktopmanager.bean.ScreenInfo;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by songzhukai on 2020/12/28.
 */
class XmlLoader {

    public static ArrayList<ScreenInfo> loadScreenInfoFromXML(Context context, int xmlID) {
        ArrayList<ScreenInfo> fromConfigXml = null;
        XmlResourceParser parser = null;
        try {
            parser = context.getResources().getXml(xmlID);
            fromConfigXml = new ArrayList<>();

            /* begin parser */
            int eventType = parser.getEventType();
            ScreenInfo screenInfo = null;
            String blank = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if ("screen".equals(tag)) {
                            screenInfo = new ScreenInfo();
                        } else if ("sortable".equals(tag)) {
                            String sortable = parser.nextText();
                            if (screenInfo != null) {
                                boolean sort = true;
                                if (!TextUtils.isEmpty(sortable) && "0".equals(sortable)) {
                                    sort = false;
                                }
                                screenInfo.setSortable(sort);
                            }
                        } else if ("removable".equals(tag)) {
                            String removable = parser.nextText();
                            if (screenInfo != null) {
                                boolean remove = true;
                                if (!TextUtils.isEmpty(removable) && "0".equals(removable)) {
                                    remove = false;
                                }
                                screenInfo.setRemovable(remove);
                            }
                        } else if ("pluginId".equals(tag)) {
                            String id = parser.nextText();
                            if (!TextUtils.isEmpty(id)) {
                                int pluginId = Integer.valueOf(id);
                                if (screenInfo != null) {
                                    screenInfo.setPluginId(pluginId);
                                }
                            }
                        } else if ("tag".equals(tag)) {
                            String screenTag = parser.nextText();
                            if (screenInfo != null) {
                                screenInfo.setTag(screenTag);
                            }
                        } else if ("versionCode".equals(tag)) {
                            String versionCode = parser.nextText();
                            if (screenInfo != null) {
                                screenInfo.setVersionCode(versionCode + blank);
                            }
                        } else if ("versionName".equals(tag)) {
                            String versionName = parser.nextText();
                            if (screenInfo != null) {
                                screenInfo.setVersionName(versionName + blank);
                            }
                        } else if ("packageName".equals(tag)) {
                            String packageName = parser.nextText();
                            if (screenInfo != null) {
                                screenInfo.setPackageName(packageName + blank);
                            }
                        } else if ("fragment".equals(tag)) {
                            String fragment = parser.nextText();
                            if (screenInfo != null) {
                                screenInfo.setMark1(fragment + blank);
                            }
                        } else if ("fileName".equals(tag)) {
                            String fileName = parser.nextText();
                            if (screenInfo != null) {
                                screenInfo.setFileName(fileName + blank);
                            }
                        } else if ("notSupport".equals(tag)) {
                            String notSupport = parser.nextText();
                            if (screenInfo != null) {
                                screenInfo.setNotSupport(notSupport);
                            }
                        } else if ("pluginType".equals(tag)) {
                            String pluginType = parser.nextText();
                            if (screenInfo != null) {
                                screenInfo.setPluginType(pluginType + blank);
                            }
                        } else if ("order".equals(tag)) {
                            String or = parser.nextText();
                            if (!TextUtils.isEmpty(or)) {
                                int order = Integer.valueOf(or);
                                if (screenInfo != null) {
                                    screenInfo.setScreenOrder(order);
                                }
                            }
                        } else if ("showOnTab".equals(tag)) {
                            String showOnTab = parser.nextText();
                            if (screenInfo != null) {
                                boolean show = false;
                                if (!TextUtils.isEmpty(showOnTab) && "1".equals(showOnTab)) {
                                    show = true;
                                }
                                screenInfo.setShowOnTab(show);
                            }
                        } else if ("local".equals(tag)) {
                            String local = parser.nextText();
                            if (screenInfo != null) {
                                boolean localed = false;
                                if (!TextUtils.isEmpty(local) && "1".equals(local)) {
                                    localed = true;
                                }
                                screenInfo.setLocal(localed);
                            }
                        } else if ("forceUpdate".equals(tag)) {
                            String forceUpdate = parser.nextText();
                            if (screenInfo != null) {
                                boolean force = false;
                                if (!TextUtils.isEmpty(forceUpdate) && "1".equals(forceUpdate)) {
                                    force = true;
                                }
                                screenInfo.setForceUpdateFromXml(force);
                            }
                        } else if ("md5".equals(tag)) {
                            String md5 = parser.nextText();
                            if (screenInfo != null) {
                                screenInfo.setMd5(md5);
                            }
                        } else if ("pageId".equals(tag)) {
                            String pageId = parser.nextText();
                            if (screenInfo != null && !TextUtils.isEmpty(pageId)) {
                                screenInfo.setPageId(pageId);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (screenInfo != null) {
                            String endTag = parser.getName();
                            if ("screen".equals(endTag)) {
                                screenInfo.setPluginState(ScreenInfo.PLUGIN_STATE_AVAILABLE);
                                fromConfigXml.add(screenInfo);
                            }
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
//            LetvLog.d(TAG, " catch error, loadScreenInfoFromXML" + e);
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
        return fromConfigXml;
    }
}
