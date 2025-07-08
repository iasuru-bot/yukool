import { BrowserRouter as Router, Routes, Route, Link, Link as RouterLink } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Container, Button, Box } from '@mui/material';
import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import TopByBrand from './pages/TopByBrand';
import TopByCategory from './pages/TopByCategory';
import TopByBrandCategory from './pages/TopByBrandCategory';
import TopIngredients from './pages/TopIngredients';
import TopAllergens from './pages/TopAllergens';
import TopAdditives from './pages/TopAdditives';
import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import SpaIcon from '@mui/icons-material/Spa';
import WarningAmberIcon from '@mui/icons-material/WarningAmber';
import ScienceIcon from '@mui/icons-material/Science';
import Paper from '@mui/material/Paper';
import { useLocation, useNavigate } from 'react-router-dom';
import MenuIcon from '@mui/icons-material/Menu';
import IconButton from '@mui/material/IconButton';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import HomeIcon from '@mui/icons-material/Home';
import Home from './pages/Home';


function App() {
  const location = useLocation();
  const navigate = useNavigate();
  const [navValue, setNavValue] = useState(() => {
    if (location.pathname.startsWith('/top-by-brand') || location.pathname.startsWith('/top-by-category') || location.pathname.startsWith('/top-by-brand-category')) return 0;
    if (location.pathname.startsWith('/top-ingredients')) return 1;
    if (location.pathname.startsWith('/top-allergens')) return 2;
    if (location.pathname.startsWith('/top-additives')) return 3;
    return false;
  });
  const [drawerOpen, setDrawerOpen] = useState(false);

  const handleNavChange = (event, newValue) => {
    setNavValue(newValue);
    switch (newValue) {
      case 0:
        navigate('/top-by-brand');
        break;
      case 1:
        navigate('/top-ingredients');
        break;
      case 2:
        navigate('/top-allergens');
        break;
      case 3:
        navigate('/top-additives');
        break;
      default:
        navigate('/');
    }
  };

  const menuItems = [
    { label: 'Accueil', icon: <HomeIcon />, path: '/' },
    { label: 'Produits', icon: <ShoppingCartIcon />, path: '/top-by-brand' },
    { label: 'Ingrédients', icon: <SpaIcon />, path: '/top-ingredients' },
    { label: 'Allergènes', icon: <WarningAmberIcon />, path: '/top-allergens' },
    { label: 'Additifs', icon: <ScienceIcon />, path: '/top-additives' },
  ];

  return (
    <>
      <AppBar position="fixed" color="default" elevation={1} sx={{ zIndex: 1201 }}>
        <Toolbar sx={{ justifyContent: { xs: 'space-between', sm: 'center' }, px: { xs: 1, sm: 2 } }}>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <IconButton edge="start" color="inherit" aria-label="menu" sx={{ mr: 1, display: { sm: 'none' } }} onClick={() => setDrawerOpen(true)}>
              <MenuIcon />
            </IconButton>
            <Box sx={{ display: { xs: 'none', sm: 'flex' }, alignItems: 'center', cursor: 'pointer' }} component={RouterLink} to="/">
              <img src="/vite.svg" alt="Logo" style={{ width: 32, height: 32, marginRight: 8 }} />
              <Typography variant="h6" component="div" fontWeight={700} color="#5B5BFF">Yukool</Typography>
            </Box>
          </Box>
          <Typography
            variant="h6"
            component={RouterLink}
            to="/"
            fontWeight={700}
            color="#5B5BFF"
            sx={{ display: { xs: 'flex', sm: 'none' }, flexGrow: 1, justifyContent: 'center', textDecoration: 'none', cursor: 'pointer' }}
          >
            Yukool
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer anchor="left" open={drawerOpen} onClose={() => setDrawerOpen(false)} sx={{ zIndex: 1300 }}>
        <Box sx={{ width: 220 }} role="presentation" onClick={() => setDrawerOpen(false)}>
          <List>
            {menuItems.map((item) => (
              <ListItem key={item.label} disablePadding>
                <ListItemButton onClick={() => navigate(item.path)}>
                  <ListItemIcon>{item.icon}</ListItemIcon>
                  <ListItemText primary={item.label} />
                </ListItemButton>
              </ListItem>
            ))}
          </List>
        </Box>
      </Drawer>
      <Box sx={{ mt: { xs: 7, sm: 8 }, mb: { xs: 10, sm: 0 } }}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/top-by-brand" element={<TopByBrand />} />
          <Route path="/top-by-category" element={<TopByCategory />} />
          <Route path="/top-by-brand-category" element={<TopByBrandCategory />} />
          <Route path="/top-ingredients" element={<TopIngredients />} />
          <Route path="/top-allergens" element={<TopAllergens />} />
          <Route path="/top-additives" element={<TopAdditives />} />
        </Routes>
      </Box>
      <Paper sx={{ position: 'fixed', left: 0, right: 0, bottom: 16, mx: 'auto', width: '95%', maxWidth: 420, borderRadius: 4, boxShadow: 6, display: { xs: 'flex', sm: 'none' }, zIndex: 1202, justifyContent: 'center' }} elevation={6}>
        <BottomNavigation
          value={navValue}
          onChange={handleNavChange}
          showLabels
          sx={{
            borderRadius: 4,
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            width: '100%',
            px: 0,
            boxSizing: 'border-box',
          }}
        >
          <BottomNavigationAction label="Produits" icon={<ShoppingCartIcon />} sx={{ flex: 1, minWidth: 0, maxWidth: 'none' }} />
          <BottomNavigationAction label="Ingrédients" icon={<SpaIcon />} sx={{ flex: 1, minWidth: 0, maxWidth: 'none' }} />
          <BottomNavigationAction label="Allergènes" icon={<WarningAmberIcon />} sx={{ flex: 1, minWidth: 0, maxWidth: 'none' }} />
          <BottomNavigationAction label="Additifs" icon={<ScienceIcon />} sx={{ flex: 1, minWidth: 0, maxWidth: 'none' }} />
        </BottomNavigation>
      </Paper>
    </>
  );
}

export default App
