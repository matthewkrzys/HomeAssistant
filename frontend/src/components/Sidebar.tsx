import { VStack, Button, Box, Text } from "@chakra-ui/react";
import { ViewType } from "../App";

interface SidebarProps {
  onSelect: (view: ViewType) => void;
  activeView: ViewType;
}

const Sidebar = ({ onSelect, activeView }: SidebarProps) => {
  return (
    <Box
      width="220px"
      bg="blue.600"
      color="white"
      p={5}
      display="flex"
      flexDirection="column"
      justifyContent="space-between"
    >
      <VStack align="stretch" spacing={3}>
        <Text fontSize="xl" fontWeight="bold" mb={4}>
          🛒 Home Assistant
        </Text>
        <Button
          colorScheme="whiteAlpha"
          variant={activeView === "shopping" ? "solid" : "ghost"}
          onClick={() => onSelect("shopping")}
        >
          Lista zakupów
        </Button>
      </VStack>

      <Box mt="auto" textAlign="center" fontSize="sm" opacity={0.7}>
        © 2025 HomeAssistant
      </Box>
    </Box>
  );
};

export default Sidebar;
