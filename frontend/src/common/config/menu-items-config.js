import menuNames from "./menu-name-config";

const menuItems = {
    employee: {
        items: [
            {
                id: menuNames.notification,
                loadingUrl: '/notification/query',
                label: '个人通知',
            },
            {
                id: menuNames.schedule,
                loadingUrl: '/schedule/query',
                label: '日程管理',
            },
            {
                id: menuNames.document,
                loadingUrl: '/document/query',
                label: '公司文献',
            },
            {
                id: menuNames.calendar,
                label: '考勤与请假',
            },
        ]
    },
    administrator: {
        header: '管理员',
        items: [
            {
                id: menuNames.org,
                loadingUrl: '/org/query',
                label: '组织架构管理',
            },
            {
                id: menuNames.rule,
                loadingUrl: '/user/query',
                label: '系统权限开通',
            },
            {
                id: menuNames.resource,
                loadingUrl: '/resource/query',
                label: '资源管理',
            },
            {
                id: menuNames.announcement,
                loadingUrl: '/announcement/query',
                label: '公告管理',
            },
            {
                id: menuNames.techDocument,
                label: '技术文献管理',
            },
            {
                id: menuNames.regulation,
                loadingUrl: '/regulation/query',
                label: '公司章程管理',
            },
        ]
    },
}

export default menuItems