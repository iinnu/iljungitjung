import { Drawer, IconButton } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useEffect, useState } from "react";
import {
  IoSearchOutline,
  IoDocumentTextOutline,
  IoExitOutline,
  IoPersonOutline,
} from "react-icons/io5";
import { useSelector } from "react-redux";

import styles from "@styles/common/Sidebar.module.scss";
import logo from "@assets/logo.png";
import CustomModal from "@components/common/CustomModal";
import iljung from "@assets/iljung_white.png";
import { getMyProfile, logout } from "@api/login";
import { MyProfile } from "@components/types/types";
import { setProfile } from "@modules/profile";
import { RootState } from "@modules/index";

interface MyInfoApiData {
  status: string;
  data: MyProfile;
}

const Sidebar = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const url = new URL(window.location.href);
  const [menu, setMenu] = useState(
    url.pathname.substring(1, url.pathname.length)
  );
  const [logoutOpen, setLogoutOpen] = useState(false);
  const profile = useSelector((state: RootState) => state.profile.profile);

  const handleClick = (type: string) => {
    navigate(`/${type}`);
    setMenu((menu) => type);
  };

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  useEffect(() => {
    getMyProfile((res: MyInfoApiData) => {
      dispatch(setProfile(res.data));
    });
  }, []);

  return (
    <Drawer
      variant="permanent"
      anchor="left"
      className={styles.drawer}
      PaperProps={{ className: styles.paper }}
    >
      <div className={styles.sidebar}>
        <div className={styles.top}>
          <img src={logo} onClick={() => handleClick("calendar/my")} />
          <div className={styles.icons}>
            <IconButton
              className={styles[`${menu === "search" ? "click-icon" : ""}`]}
              onClick={() => handleClick("search")}
            >
              <IoSearchOutline />
            </IconButton>
            <IconButton
              className={
                styles[`${menu === "reservation" ? "click-icon" : ""}`]
              }
              onClick={() => handleClick("reservation")}
            >
              <IoDocumentTextOutline />
            </IconButton>
          </div>
        </div>
        <div className={styles.bottom}>
          <div className={styles.icons}>
            <IconButton
              className={styles.logout}
              onClick={() => setLogoutOpen(true)}
            >
              <IoExitOutline />
            </IconButton>
            <CustomModal
              open={logoutOpen}
              setOpen={setLogoutOpen}
              confirmLabel="확인"
              cancelLabel="취소"
              handleConfirm={handleLogout}
              children={
                <div className={styles["modal-content"]}>
                  <div className={styles.img}>
                    <img src={iljung} />
                  </div>
                  <div className={styles.text}>로그아웃 하시겠습니까?</div>
                </div>
              }
            />
            <IconButton
              className={styles[`${menu === "profile" ? "click-icon" : ""}`]}
              onClick={() => handleClick("profile")}
            >
              <IoPersonOutline />
            </IconButton>
          </div>
          <img
            className={
              styles[`${menu === "calendar/my" ? "click-profile" : ""}`]
            }
            src={profile.imagePath}
            onClick={() => handleClick("calendar/my")}
          />
        </div>
      </div>
    </Drawer>
  );
};

export default Sidebar;
