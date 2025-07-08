import { useEffect, useState } from 'react';
import { Container, Typography, Box, Button, Card, CardContent, Avatar, Stack, CircularProgress, Alert } from '@mui/material';
import ScienceIcon from '@mui/icons-material/Science';
import FilterListIcon from '@mui/icons-material/FilterList';

function TopAdditives({ limit = 10 }) {
  const [additives, setAdditives] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);
    fetch(`/additives/top?limit=${limit}`)
      .then(res => {
        if (!res.ok) throw new Error('Erreur lors du chargement');
        return res.json();
      })
      .then(data => setAdditives(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, [limit]);

  return (
    <Container maxWidth="sm" sx={{ pb: { xs: 8, sm: 2 }, pt: { xs: 2, sm: 4 }, minHeight: '100vh' }}>
      <Typography variant="h5" fontWeight={700} mt={1} mb={1} sx={{ fontSize: { xs: '1.7rem', sm: '2rem' }, letterSpacing: -1, textAlign: 'center' }}>Additifs</Typography>
      <Box display="flex" alignItems="center" mb={2}>
        <Button startIcon={<FilterListIcon />} variant="outlined" sx={{ color: '#5B5BFF', borderColor: '#5B5BFF', fontWeight: 700, bgcolor: 'rgba(91,91,255,0.06)', borderRadius: 2, px: 2, textTransform: 'none', '&:hover': { bgcolor: 'rgba(91,91,255,0.12)' } }}>
          Filters
        </Button>
      </Box>
      {loading && <Box display="flex" justifyContent="center" my={4}><CircularProgress /></Box>}
      {error && <Alert severity="error">{error}</Alert>}
      <Stack spacing={2} sx={{ maxHeight: { xs: 'calc(100vh - 220px)', sm: 'none' }, overflowY: { xs: 'auto', sm: 'visible' } }}>
        {!loading && !error && additives.map((additive) => (
          <Card key={additive.id || additive.name} sx={{ borderRadius: 4, background: '#fff', boxShadow: '0 2px 12px 0 rgba(91,91,255,0.07)', px: 1, transition: 'box-shadow 0.2s', '&:active': { boxShadow: '0 4px 16px 0 rgba(91,91,255,0.15)' }, '&:hover': { boxShadow: '0 4px 16px 0 rgba(91,91,255,0.12)' } }}>
            <CardContent sx={{ display: 'flex', alignItems: 'center', p: { xs: 1.2, sm: 2 } }}>
              <Avatar sx={{ bgcolor: '#ede7f6', color: '#7c4dff', mr: 2, width: { xs: 44, sm: 56 }, height: { xs: 44, sm: 56 } }}>
                <ScienceIcon />
              </Avatar>
              <Box flex={1} minWidth={0}>
                <Typography variant="subtitle1" fontWeight={700} sx={{ fontSize: { xs: '1.08rem', sm: '1.18rem' }, color: '#222' }}>{additive.name}</Typography>
                <Typography variant="body2" color="text.secondary" sx={{ fontSize: { xs: '0.95rem', sm: '1.05rem' } }}>{additive.description}</Typography>
              </Box>
              <Box textAlign="right" ml={2}>
                <Avatar sx={{ bgcolor: '#e8f5e9', color: '#43a047', width: { xs: 32, sm: 38 }, height: { xs: 32, sm: 38 }, fontWeight: 700, fontSize: { xs: 15, sm: 18 } }}>{additive.count}</Avatar>
              </Box>
            </CardContent>
          </Card>
        ))}
      </Stack>
    </Container>
  );
}

export default TopAdditives; 