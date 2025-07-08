import { Box, Container, Typography, Stack, Paper } from '@mui/material';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import SpaIcon from '@mui/icons-material/Spa';
import WarningAmberIcon from '@mui/icons-material/WarningAmber';
import ScienceIcon from '@mui/icons-material/Science';
import { useNavigate } from 'react-router-dom';

const actions = [
  {
    label: 'PRODUITS',
    icon: <ShoppingCartIcon sx={{ color: '#5B5BFF', fontSize: 30 }} />, // bleu
    path: '/top-by-brand',
  },
  {
    label: 'INGRÉDIENTS',
    icon: <SpaIcon sx={{ color: '#43a047', fontSize: 30 }} />, // vert
    path: '/top-ingredients',
  },
  {
    label: 'ALLERGÈNES',
    icon: <WarningAmberIcon sx={{ color: '#ff9800', fontSize: 30 }} />, // orange
    path: '/top-allergens',
  },
  {
    label: 'ADDITIFS',
    icon: <ScienceIcon sx={{ color: '#7c4dff', fontSize: 30 }} />, // violet
    path: '/top-additives',
  },
];

function Home() {
  const navigate = useNavigate();
  return (
    <Box sx={{ minHeight: 'calc(100vh - 110px)', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
      <Container maxWidth="sm">
        <Box sx={{ textAlign: 'center', p: { xs: 2, sm: 4 }, borderRadius: 6, background: 'rgba(91,91,255,0.03)', display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>
          <img src="/vite.svg" alt="Yukool" style={{ width: 64, marginBottom: 16 }} />
          <Typography variant="h3" fontWeight={800} color="#5B5BFF" mb={1} sx={{ fontSize: { xs: '2rem', sm: '2.5rem' } }}>Yukool</Typography>
          <Typography variant="subtitle1" color="text.secondary" mb={3}>
            Explorez les produits, ingrédients, allergènes et additifs les plus courants de l'alimentation.
          </Typography>
          <Stack spacing={2}>
            {actions.map((action) => (
              <Paper
                key={action.label}
                elevation={4}
                onClick={() => navigate(action.path)}
                sx={{
                  cursor: 'pointer',
                  bgcolor: '#fff',
                  borderRadius: 4,
                  width: '70%',
                  height: 70,
                  display: 'flex',
                  flexDirection: 'row',
                  alignItems: 'center',
                  justifyContent: 'center', // centrer le contenu
                  px: 3,
                  mx: 'auto', // centrer le bouton dans la colonne
                  boxShadow: '0 2px 12px 0 rgba(91,91,255,0.07)',
                  transition: 'box-shadow 0.2s, border-color 0.2s, background 0.2s',
                  border: '2px solid #f0f1fa',
                  '&:hover': {
                    boxShadow: '0 4px 16px 0 rgba(91,91,255,0.12)',
                    borderColor: '#bdbdbd',
                    background: '#fafbff',
                  },
                }}
              >
                <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', mr: 1.5 }}>
                  {action.icon}
                </Box>
                <Typography variant="h6" fontWeight={700} sx={{ fontSize: { xs: '1rem', sm: '1rem' }, color: '#222', letterSpacing: 1, textAlign: 'center' }}>{action.label}</Typography>
              </Paper>
            ))}
          </Stack>
        </Box>
      </Container>
    </Box>
  );
}

export default Home;