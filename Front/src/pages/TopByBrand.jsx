import { useEffect, useState } from 'react';
import { Container, Typography, Box, Card, CardContent, Avatar, Stack, Tabs, Tab, CircularProgress, Alert } from '@mui/material';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import CategoryIcon from '@mui/icons-material/Category';
import GroupWorkIcon from '@mui/icons-material/GroupWork';
import ImageIcon from '@mui/icons-material/Image';

function TopByBrand({ brand = 'Nature Valley', limit = 10 }) {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);
    fetch(`/products/top-by-brand?brand=${encodeURIComponent(brand)}&limit=${limit}`)
      .then(res => {
        if (!res.ok) throw new Error('Erreur lors du chargement');
        return res.json();
      })
      .then(data => setProducts(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, [brand, limit]);

  return (
    <Container maxWidth="sm" sx={{ pb: { xs: 8, sm: 2 }, pt: { xs: 2, sm: 4 }, minHeight: '100vh', px: { xs: 0.5, sm: 2 } }}>
      <Typography variant="h5" fontWeight={700} mt={1} mb={1} sx={{ fontSize: { xs: '1.3rem', sm: '2rem' }, letterSpacing: -1, textAlign: 'center' }}>Produits</Typography>
      <Tabs value={0} variant="fullWidth" sx={{ mb: 2, width: '100%', minWidth: 0, '.MuiTabs-flexContainer': { justifyContent: 'center' } }} TabIndicatorProps={{ style: { height: 3, borderRadius: 2 } }}>
        <Tab label={<Box sx={{ fontWeight: 600, fontSize: { xs: '0.6rem', sm: '1rem' } }}>Marque</Box>} icon={<ShoppingCartIcon />} iconPosition="start" sx={{ minWidth: 0 }} />
        <Tab label={<Box sx={{ fontWeight: 500, fontSize: { xs: '0.6rem', sm: '1rem' } }}>Catégorie</Box>} icon={<CategoryIcon />} iconPosition="start" component="a" href="/top-by-category" sx={{ minWidth: 0 }} />
        <Tab label={<Box sx={{ fontWeight: 500, fontSize: { xs: '0.6rem', sm: '1rem' } }}>Marque + Catégorie</Box>} icon={<GroupWorkIcon />} iconPosition="start" component="a" href="/top-by-brand-category" sx={{ minWidth: 0 }} />
      </Tabs>
      {loading && <Box display="flex" justifyContent="center" my={4}><CircularProgress /></Box>}
      {error && <Alert severity="error">{error}</Alert>}
      <Stack spacing={1.5} sx={{ maxWidth: '100%', width: '100%', maxHeight: { xs: 'calc(100vh - 260px)', sm: 'none' }, overflowY: { xs: 'auto', sm: 'visible' }, pr: 0 }}>
        {!loading && !error && products.map((product) => (
          <Card key={product.id || product.name} sx={{ borderRadius: 3, background: '#fff', boxShadow: '0 2px 12px 0 rgba(91,91,255,0.07)', px: 0.5, width: '95%', maxWidth: '100%', overflow: 'hidden' }}>
            <CardContent sx={{ display: 'flex', alignItems: 'center', p: { xs: 0.5, sm: 1.5 } }}>
              {product.image ? (
                <Avatar src={product.image} variant="rounded" sx={{ width: { xs: 32, sm: 48 }, height: { xs: 32, sm: 48 }, mr: 1, bgcolor: '#e3e6ff', flexShrink: 0 }} />
              ) : (
                <Avatar variant="rounded" sx={{ width: { xs: 32, sm: 48 }, height: { xs: 32, sm: 48 }, mr: 1, bgcolor: '#e3e6ff', color: '#bdbdbd', flexShrink: 0 }}>
                  <ImageIcon fontSize="small" />
                </Avatar>
              )}
              <Box flex={1} minWidth={0} sx={{ overflow: 'hidden' }}>
                <Typography variant="subtitle1" fontWeight={700} sx={{ fontSize: { xs: '0.98rem', sm: '1.18rem' }, color: '#222', wordBreak: 'break-word', lineHeight: 1.2 }}>{product.name}</Typography>
                <Typography variant="body2" fontWeight={600} color="#5B5BFF" sx={{ fontSize: { xs: '0.9rem', sm: '1.05rem' }, wordBreak: 'break-word', lineHeight: 1.1 }}>{product.brand || brand}</Typography>
                <Typography variant="body2" color="text.secondary" sx={{ fontSize: { xs: '0.75rem', sm: '0.95rem' }, wordBreak: 'break-word', lineHeight: 1.1 }}>{product.category}</Typography>
              </Box>
              <Box textAlign="right" ml={0.5}>
                <Avatar sx={{ bgcolor: '#e8f5e9', color: '#43a047', width: { xs: 24, sm: 32 }, height: { xs: 24, sm: 32 }, fontWeight: 700, fontSize: { xs: 12, sm: 16 } }}>{product.score}</Avatar>
              </Box>
            </CardContent>
          </Card>
        ))}
      </Stack>
    </Container>
  );
}

export default TopByBrand; 