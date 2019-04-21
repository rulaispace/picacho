import menuNames from "./menu-name-config";
import commonNames from "../../common/config/common-name-config";

const mainMenu = {
    header: '',
    items: [
        {
            id: menuNames.notification,
            rule: commonNames.employee,
            loadingUrl: '/notification/query',
            label: '个人通知',
        },
        {
            id: menuNames.schedule,
            rule: commonNames.employee,
            loadingUrl: '/schedule/query',
            label: '日程管理',
        },
        {
            id: menuNames.document,
            rule: commonNames.employee,
            loadingUrl: '/document/query',
            label: '公司文献',
        },
        {
            id: menuNames.calendar,
            rule: commonNames.employee,
            label: '考勤与请假',
        },
        {
            id: menuNames.org,
            rule: commonNames.employee,
            loadingUrl: '/org/query',
            label: '组织架构管理',
        },
        {
            id: menuNames.rule,
            rule: commonNames.admin,
            loadingUrl: '/user/query',
            label: '系统权限开通',
        },
        {
            id: menuNames.resource,
            rule: commonNames.admin,
            loadingUrl: '/resource/query',
            label: '资源管理',
        },
        {
            id: menuNames.announcement,
            rule: commonNames.admin,
            loadingUrl: '/announcement/query',
            label: '公告管理',
        },
        {
            id: menuNames.techDocument,
            rule: commonNames.admin,
            label: '技术文献管理',
        },
        {
            id: menuNames.regulation,
            rule: commonNames.admin,
            loadingUrl: '/regulation/query',
            label: '公司章程管理',
        },
    ]
}


export default mainMenu