import { useEffect, useState } from 'react';
import { Container, Typography, Box, Card, CardContent, Avatar, Stack, Tabs, Tab, CircularProgress, Alert, TextField, Slider, Button, Chip, Tooltip } from '@mui/material';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import CategoryIcon from '@mui/icons-material/Category';
import GroupWorkIcon from '@mui/icons-material/GroupWork';
import ImageIcon from '@mui/icons-material/Image';
import SpaIcon from '@mui/icons-material/Spa';
import ScienceIcon from '@mui/icons-material/Science';
import WarningAmberIcon from '@mui/icons-material/WarningAmber';

function nutriColor(grade) {
  switch ((grade||'').toUpperCase()) {
    case 'A': return '#43a047';
    case 'B': return '#8bc34a';
    case 'C': return '#ffc107';
    case 'D': return '#ff9800';
    case 'E': return '#e53935';
    default: return '#bdbdbd';
  }
}

function TopByBrandCategory() {
  const [brand, setBrand] = useState('Green Life');
  const [category, setCategory] = useState('Dairy Alternatives');
  const [limit, setLimit] = useState(10);
  const [searchBrand, setSearchBrand] = useState('Green Life');
  const [searchCategory, setSearchCategory] = useState('Dairy Alternatives');
  const [searchLimit, setSearchLimit] = useState(10);
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSearch = () => {
    setSearchBrand(brand);
    setSearchCategory(category);
    setSearchLimit(limit);
  };

  useEffect(() => {
    setLoading(true);
    setError(null);
    fetch(`/products/top-by-brand-category?brand=${encodeURIComponent(searchBrand)}&category=${encodeURIComponent(searchCategory)}&limit=${searchLimit}`)
      .then(res => {
        if (!res.ok) throw new Error('Erreur lors du chargement');
        return res.json();
      })
      .then(data => setProducts(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, [searchBrand, searchCategory, searchLimit]);

  return (
    <Container maxWidth="sm" sx={{ pb: { xs: 8, sm: 2 }, pt: { xs: 2, sm: 4 }, minHeight: '100vh', px: { xs: 0.5, sm: 2 } }}>
      <Typography variant="h5" fontWeight={700} mt={1} mb={1} sx={{ fontSize: { xs: '1.3rem', sm: '2rem' }, letterSpacing: -1, textAlign: 'center' }}>Produits</Typography>
      <Tabs value={2} variant="fullWidth" sx={{ mb: 2, width: '100%', minWidth: 0, '.MuiTabs-flexContainer': { justifyContent: 'center' } }} TabIndicatorProps={{ style: { height: 3, borderRadius: 2 } }}>
        <Tab label={<Box sx={{ fontWeight: 600, fontSize: { xs: '0.6rem', sm: '1rem' } }}>Marque</Box>} icon={<ShoppingCartIcon />} iconPosition="start" component="a" href="/top-by-brand" sx={{ minWidth: 0 }} />
        <Tab label={<Box sx={{ fontWeight: 600, fontSize: { xs: '0.6rem', sm: '1rem' } }}>Catégorie</Box>} icon={<CategoryIcon />} iconPosition="start" component="a" href="/top-by-category" sx={{ minWidth: 0 }} />
        <Tab label={<Box sx={{ fontWeight: 700, fontSize: { xs: '0.6rem', sm: '1rem' } }}>Marque + Catégorie</Box>} icon={<GroupWorkIcon />} iconPosition="start" sx={{ minWidth: 0 }} />
      </Tabs>
      <Box display="flex" alignItems="center" gap={2} mb={2}>
        <TextField
          label="Marque"
          value={brand}
          onChange={e => setBrand(e.target.value)}
          size="small"
          sx={{ flex: 1 }}
        />
        <TextField
          label="Catégorie"
          value={category}
          onChange={e => setCategory(e.target.value)}
          size="small"
          sx={{ flex: 1 }}
        />
        <Box sx={{ width: 150 }}>
          <Typography id="limit-slider" gutterBottom fontSize={13}>
            Limite : {limit}
          </Typography>
          <Slider
            value={limit}
            min={1}
            max={50}
            step={1}
            onChange={(_, v) => setLimit(v)}
            aria-labelledby="limit-slider"
            size="small"
          />
        </Box>
        <Button variant="contained" onClick={handleSearch} sx={{ height: 40, minWidth: 100 }}>Rechercher</Button>
      </Box>
      {loading && <Box display="flex" justifyContent="center" my={4}><CircularProgress /></Box>}
      {error && <Alert severity="error">{error}</Alert>}
      <Stack spacing={1.5} sx={{ maxWidth: '100%', width: '100%', maxHeight: { xs: 'calc(100vh - 260px)', sm: 'none' }, overflowY: { xs: 'auto', sm: 'visible' }, pr: 0 }}>
        {!loading && !error && products.map((product) => (
          <Card
            key={product.id || product.nom}
            sx={{
              borderRadius: 5,
              background: '#fff',
              boxShadow: '0 4px 24px 0 rgba(91,91,255,0.13)',
              px: 0.5,
              width: '95%',
              maxWidth: '100%',
              overflow: 'hidden',
              transition: 'box-shadow 0.2s, background 0.2s, transform 0.15s',
              my: 2,
              '&:hover': {
                boxShadow: '0 8px 32px 0 rgba(91,91,255,0.22)',
                background: '#f7faff',
                transform: 'translateY(-2px) scale(1.012)',
              },
            }}
          >
            <CardContent sx={{ p: { xs: 1, sm: 2 } }}>
              <Box display="flex" alignItems="center" gap={1} mb={0.5}>
                <Box flex={1} minWidth={0}>
                  <Typography variant="subtitle1" fontWeight={700} sx={{ fontSize: { xs: '1.08rem', sm: '1.18rem' }, color: '#222', wordBreak: 'break-word', lineHeight: 1.2 }}>{product.nom}</Typography>
                  <Typography variant="body2" fontWeight={600} color="#5B5BFF" sx={{ fontSize: { xs: '0.9rem', sm: '1.05rem' }, wordBreak: 'break-word', lineHeight: 1.1 }}>{product.marque || searchBrand}</Typography>
                  <Typography variant="body2" color="text.secondary" sx={{ fontSize: { xs: '0.8rem', sm: '0.95rem' }, wordBreak: 'break-word', lineHeight: 1.1 }}>{product.categorie || searchCategory}</Typography>
                </Box>
                <Tooltip title={`Nutri-Score ${product.nutritionGradeFr || '?'}`}> 
                  <Avatar sx={{ bgcolor: nutriColor(product.nutritionGradeFr), color: '#fff', width: 38, height: 38, fontWeight: 700, fontSize: 22 }}>
                    {product.nutritionGradeFr || '?'}
                  </Avatar>
                </Tooltip>
              </Box>
              <Box display="flex" flexWrap="wrap" gap={1} mb={1}>
                <Chip label={`Énergie: ${product.energie100g ?? '?'} kcal`} size="small" />
                <Chip label={`Graisse: ${product.graisse100g ?? '?'}g`} size="small" />
                <Chip label={`Sucres: ${product.sucres100g ?? '?'}g`} size="small" />
                <Chip label={`Protéines: ${product.proteines100g ?? '?'}g`} size="small" />
                <Chip label={`Sel: ${product.sel100g ?? '?'}g`} size="small" />
                {product.presenceHuilePalme && <Chip label="Huile de palme" color="warning" size="small" />}
              </Box>
              <Box mb={0.5}>
                <Typography variant="body2" fontWeight={600} sx={{ fontSize: '0.95em', mb: 0.2 }}>Ingrédients :</Typography>
                <Typography variant="body2" color="text.secondary" sx={{ fontSize: '0.93em' }}>{Array.isArray(product.ingredients) ? product.ingredients.join(', ') : product.ingredients}</Typography>
              </Box>
              {product.additifs && product.additifs.length > 0 && (
                <Box mb={0.5}>
                  <Typography variant="body2" fontWeight={600} sx={{ fontSize: '0.95em', mb: 0.2 }}>Additifs :</Typography>
                  <Stack direction="row" spacing={1} flexWrap="wrap" sx={{ rowGap: 1.2 }}>
                    {product.additifs.map((a, i) => (
                      <Chip key={i} icon={<ScienceIcon fontSize="small" />} label={a} size="small" />
                    ))}
                  </Stack>
                </Box>
              )}
              {product.allergenes && product.allergenes.length > 0 && (
                <Box mb={0.5}>
                  <Typography variant="body2" fontWeight={600} sx={{ fontSize: '0.95em', mb: 0.2 }}>Allergènes :</Typography>
                  <Stack direction="row" spacing={1} flexWrap="wrap">
                    {product.allergenes.map((a, i) => (
                      <Chip key={i} icon={<WarningAmberIcon fontSize="small" />} label={a} size="small" color="error" />
                    ))}
                  </Stack>
                </Box>
              )}
            </CardContent>
          </Card>
        ))}
      </Stack>
    </Container>
  );
}

export default TopByBrandCategory; 