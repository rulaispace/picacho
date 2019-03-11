import React from 'react'
import PropTypes from 'prop-types'

import CalendarViewDayIcon from '@material-ui/icons/CalendarViewDay';
import NotificationIcon from '@material-ui/icons/Notifications'
import SecurityIcon from '@material-ui/icons/Security';
import FolderIcon from '@material-ui/icons/Folder';
import ScheduleIcon from '@material-ui/icons/Schedule';
import HomeIcon from '@material-ui/icons/Home';
import DevicesIcon from '@material-ui/icons/Devices';
import AnnouncementIcon from '@material-ui/icons/Announcement';
import ComputerIcon from '@material-ui/icons/Computer';
import FolderSpecialIcon from '@material-ui/icons/FolderSpecial';
import MenuIcon from '@material-ui/icons/Menu'
import SearchIcon from '@material-ui/icons/Search'
import AccountIcon from '@material-ui/icons/AccountCircleOutlined'
import MailIcon from '@material-ui/icons/Mail'
import CommitIcon from '@material-ui/icons/DoneOutline'
import ResetIcon from '@material-ui/icons/DeleteOutline'
import FileIcon from '@material-ui/icons/AttachFile'
import UploadIcon from '@material-ui/icons/CloudUpload'
import ArrowRightIcon from '@material-ui/icons/ArrowRightOutlined'
import ArrowDownIcon from '@material-ui/icons/ArrowDropDownOutlined'
import GroupIcon from '@material-ui/icons/GroupOutlined'
import GroupAddIcon from '@material-ui/icons/GroupAddOutlined'
import PersonIcon from '@material-ui/icons/PersonOutline'
import PersonAddIcon from '@material-ui/icons/PersonAddOutlined'
import EditIcon from '@material-ui/icons/EditOutlined'
import DeleteIcon from '@material-ui/icons/DeleteOutline'
import FilterListIcon from '@material-ui/icons/FilterList'
import SkipPreIcon from '@material-ui/icons/SkipPreviousOutlined'
import SkipNextIcon from '@material-ui/icons/SkipNextOutlined'
import CloseIcon from '@material-ui/icons/Close'
import CheckIcon from '@material-ui/icons/Check'
import DomainIcon from '@material-ui/icons/Domain'
import DomainSharpIcon from '@material-ui/icons/DomainSharp'
import AddIcon from '@material-ui/icons/PlaylistAddRounded'
import iconNames from "../config/icon-name-config";
import menuNames from "../config/menu-name-config";
import OpenIcon from '@material-ui/icons/OpenInBrowser';
import ClearIcon from '@material-ui/icons/Clear'
import RemoveIcon from '@material-ui/icons/Remove'

export default function IconStore({iconKey, fontSize, className}) {
    switch (iconKey) {
        case menuNames.calendar:
            return <CalendarViewDayIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case menuNames.notification:
            return <NotificationIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case menuNames.document:
            return <FolderIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case menuNames.schedule:
            return <ScheduleIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case menuNames.org:
            return <HomeIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case menuNames.rule:
            return <SecurityIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case menuNames.resource:
            return <DevicesIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case menuNames.announcement:
            return <AnnouncementIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case menuNames.techDocument:
            return <ComputerIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case menuNames.regulation:
            return <FolderSpecialIcon fontSize={fontSize?fontSize:'default'} className={className}/>

        // 图标元素
        case iconNames.menu:
            return <MenuIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.search:
            return <SearchIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.mail:
            return <MailIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.notification:
            return <NotificationIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.account:
            return <AccountIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.reset:
            return <ResetIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.commit:
            return <CommitIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.attachment:
            return <FileIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.folder:
            return <FolderIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.upload:
            return <UploadIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.add:
            return <AddIcon fontSize={fontSize?fontSize:'default'} className={className}/>

        case iconNames.arrowRight:
            return <ArrowRightIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.arrowDown:
            return <ArrowDownIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.group:
            return <GroupIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.groupAdd:
            return <GroupAddIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.person:
            return <PersonIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.personAdd:
            return <PersonAddIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.edit:
            return <EditIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.delete:
            return <DeleteIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.filterList:
            return <FilterListIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.skipPre:
            return <SkipPreIcon  fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.skipNext:
            return <SkipNextIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.close:
            return <CloseIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.check:
            return <CheckIcon fontSize={fontSize?fontSize:'default'} className={className}/>

        case iconNames.domain:
            return <DomainIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.domainSharp:
            return <DomainSharpIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.open:
            return <OpenIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.clear:
            return <ClearIcon fontSize={fontSize?fontSize:'default'} className={className}/>
        case iconNames.remove:
            return <RemoveIcon fontSize={fontSize?fontSize:'default'} className={className}/>


        default:
            return null
    }
}

IconStore.propTypes = {
    iconKey: PropTypes.string.isRequired,
    fontSize: PropTypes.string,
    rootClassName: PropTypes.object,
}